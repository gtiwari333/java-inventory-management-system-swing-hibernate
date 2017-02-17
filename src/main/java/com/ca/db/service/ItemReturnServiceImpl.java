package com.ca.db.service;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.ItemReturn;
import com.ca.db.model.Nikasa;
import com.ca.db.service.dto.ReturnedItemDTO;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemReturnServiceImpl extends BaseDAO {

    public ItemReturnServiceImpl() throws Exception {
        super();
    }

    public void editReturnedItem(int returnId, int qty, Date returnDate, int damageStatus, String returnNumber) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {

            Criteria c = s.createCriteria(ItemReturn.class);
            c.add(Restrictions.eq("id", returnId));
            ItemReturn ret = (ItemReturn) (c.list()).get(0);

            Nikasa nikasa = ret.getNikasa();
            Item item = nikasa.getItem();
            //if account already transferred to new, do not change
            if (item.getAddedType() == Item.ACCOUNT_TRANSFERRED_TO_NEW) {
                throw new Exception(" This cannot be modified.");
            }
            /*
             * adjust item stock qty
			 */
            item.setQuantity(item.getQuantity() - ret.getQuantity() + qty);

			/*
             * adjust nikasa remaining qty
			 */
            nikasa.setRemainingQtyToReturn(nikasa.getRemainingQtyToReturn() + ret.getQuantity() - qty);

            ret.setAddedDate(returnDate);
            ret.setReturnItemCondition(damageStatus);
            ret.setReturnNumber(returnNumber);

            ret.setQuantity(qty);
            /**
             * update
             */
            s.update(ret);
            s.update(item);
            s.update(nikasa);

            tx.commit();
        } catch (Exception er) {
            tx.rollback();
            er.printStackTrace();
            throw new Exception("Item return update failed " + er.getMessage());
        }
    }

    public void deleteReturnedItem(int returnId) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {

            Criteria c = s.createCriteria(ItemReturn.class);
            c.add(Restrictions.eq("id", returnId));
            ItemReturn ret = (ItemReturn) (c.list()).get(0);

            Nikasa nikasa = ret.getNikasa();
            Item item = nikasa.getItem();
            //if account already transferred to new, do not change
            if (item.getAddedType() == Item.ACCOUNT_TRANSFERRED_TO_NEW) {
                throw new Exception(" This cannot be modified.");
            }
            // qty in stock, subtract previous returned qty (since it is
            // deleted)
            item.setQuantity(item.getQuantity() - ret.getQuantity());
            // nikasa - add remaining qty to return , since returned record is
            // being deleted

            nikasa.setRemainingQtyToReturn(nikasa.getRemainingQtyToReturn() + ret.getQuantity());

            ret.setdFlag(0);
            s.update(ret);
            s.update(item);
            s.update(nikasa);

            tx.commit();
        } catch (Exception er) {
            tx.rollback();
            er.printStackTrace();
            throw new Exception("Item return delete failed " + er.getMessage());
        }

    }

    public List<ItemReturn> itemReturnQuery(String itemName, int categoryId, int selectedReceiverType, int receiverId, int returnedStatus,
                                            String nikasaNumber, String returnNumber, Date fromDate, Date toDate) throws Exception {
        Criteria c = getSession().createCriteria(ItemReturn.class);

        c.add(Restrictions.eq("dFlag", 1));
        c.createAlias("nikasa", "nik");
        c.createAlias("nikasa.item", "it");

        if (!StringUtils.isEmpty(nikasaNumber)) {

            c.add(Restrictions.eq("nik.nikasaPanaNumber", nikasaNumber));
        }

        if (!StringUtils.isEmpty(returnNumber)) {
            c.add(Restrictions.eq("returnNumber", returnNumber));
        }
        // join nikasa-type
        if (selectedReceiverType > 0) {
            c.add(Restrictions.eq("nik.nikasaType", selectedReceiverType));
        }

        if (returnedStatus >= 0) {
            c.add(Restrictions.eq("nik.status", returnedStatus));
        }

        if (receiverId > 0) {
            if (selectedReceiverType == Nikasa.PERSONNAL) {
                c.createAlias("nikasa.person", "per");
                c.add(Restrictions.eq("per.id", receiverId));

            } else if (selectedReceiverType == Nikasa.OFFICIAL) {
                c.createAlias("nikasa.branchOffice", "bro");
                c.add(Restrictions.eq("bro.id", receiverId));
            }
            // if any other
        }
        if (!StringUtils.isEmpty(itemName)) {
            c.add(Restrictions.ilike("it.name", "%" + itemName.toLowerCase() + "%", MatchMode.ANYWHERE));
        }

        // http://stackoverflow.com/questions/8726396/hibernate-criteria-join-with-3-tables
        c.createAlias("it.category", "cat");
        // only returnable
        c.add(Restrictions.eq("cat.categoryType", Category.TYPE_RETURNABLE));

        if (categoryId > 0) {

            c.add(Restrictions.eq("cat.id", categoryId));
        }

        if (!DateTimeUtils.isEmpty(fromDate)) {
            c.add(Restrictions.ge("addedDate", fromDate));
        }
        if (!DateTimeUtils.isEmpty(toDate)) {
            c.add(Restrictions.le("addedDate", toDate));
        }

        return c.list();
    }

    public List<Category> getNonReturnableCategory() throws Exception {
        Criteria c = getSession().createCriteria(Category.class);
        c.add(Restrictions.eq("categoryType", Category.TYPE_RETURNABLE));
        return c.list();

    }

    /**
     * FIXME: do i save returned item to Item table ?
     */
    public void saveReturnedItem(Map<Integer, ReturnedItemDTO> cartMap, String returnNumber) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {
            for (Entry<Integer, ReturnedItemDTO> entry : cartMap.entrySet()) {

                int itemId = entry.getKey();
                ReturnedItemDTO ret = entry.getValue();
                Integer qty = ret.qty;
                // TODO: return item status
                Integer damageStatus = ret.damageStatus;

                Criteria c = s.createCriteria(Nikasa.class);
                c.add(Restrictions.eq("id", itemId));
                Nikasa nikasa = (Nikasa) (c.list()).get(0);

                ItemReturn itemReturn = new ItemReturn();
                itemReturn.setdFlag(1);
                itemReturn.setNikasa(nikasa);
                itemReturn.setQuantity(qty);

                if (qty == nikasa.getRemainingQtyToReturn()) {
                    nikasa.setStatus(Nikasa.STATUS_RETURNED_ALL);
                }
                nikasa.setRemainingQtyToReturn(nikasa.getRemainingQtyToReturn() - qty);
                nikasa.setLastModifiedDate(new Date());
                // add return number
                itemReturn.setReturnNumber(returnNumber);
                itemReturn.setAddedDate(new Date());
                itemReturn.setReturnItemCondition(damageStatus);

                Item itemOld = nikasa.getItem();
                // should not be >item.originalquantity
                itemOld.setQuantity(itemOld.getQuantity() + qty);
                itemOld.setdFlag(1);
                itemOld.setRackNo(itemOld.getRackNo());

                // TODO: ask - if we add exemption item/needs repair/?? to item?
                if (damageStatus == ItemReturn.RETURN_ITEM_CONDITION_GOOD) {
                    s.update(itemOld);
                } else {

                    Item newBo = new Item();
                    newBo.setPurchaseOrderNo(itemOld.getPurchaseOrderNo());
                    newBo.setPurchaseDate(itemOld.getPurchaseDate());
                    newBo.setName(itemOld.getName());
                    newBo.setKhataNumber(itemOld.getKhataNumber());
                    newBo.setKhataNumber(itemOld.getKhataNumber());
                    newBo.setDakhilaNumber(itemOld.getDakhilaNumber());
                    newBo.setPanaNumber(itemOld.getPanaNumber());
                    newBo.setRackNo(itemOld.getRackNo());
                    newBo.setRate(itemOld.getRate());
                    newBo.setPartsNumber(itemOld.getPartsNumber());
                    newBo.setOriginalQuantity(itemOld.getOriginalQuantity());
                    newBo.setQuantity(itemOld.getQuantity());
                    newBo.setSerialNumber(itemOld.getSerialNumber());
                    newBo.setPurchaseDate(itemOld.getPurchaseDate());
                    newBo.setAddedType(Item.ACCOUNT_TRANSFERRED_TO_NEW);
                    newBo.setCategory(itemOld.getCategory());
                    newBo.setSpecification(itemOld.getSpecification());
                    newBo.setVendor(itemOld.getVendor());
                    newBo.setUnitsString(itemOld.getUnitsString());
                    newBo.setStatus(damageStatus);
                }

                s.update(nikasa);
                s.save(itemReturn);
                // TODO: order table - status change

            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw new Exception("Item return failed " + e.getMessage());
        }
    }

}
