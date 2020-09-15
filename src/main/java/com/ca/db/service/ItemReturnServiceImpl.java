package com.ca.db.service;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.ItemReturn;
import com.ca.db.model.Transfer;
import com.ca.db.service.dto.ReturnedItemDTO;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemReturnServiceImpl extends BaseDAO {

    public ItemReturnServiceImpl() throws Exception {
        super();
    }

    public static List<Category> getNonReturnableCategory() {
        Criteria c = getSession().createCriteria(Category.class);
        c.add(Restrictions.eq("categoryType", Category.TYPE_RETURNABLE));
        return c.list();

    }

    /**
     * FIXME: do i save returned item to Item table ?
     */
    public static void saveReturnedItem(Map<Integer, ReturnedItemDTO> cartMap, String returnNumber) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {
            for (Entry<Integer, ReturnedItemDTO> entry : cartMap.entrySet()) {

                int itemId = entry.getKey();
                ReturnedItemDTO ret = entry.getValue();
                int qty = ret.qty;
                // TODO: return item status
                int damageStatus = ret.damageStatus;

                Criteria c = s.createCriteria(Transfer.class);
                c.add(Restrictions.eq("id", itemId));
                Transfer transfer = (Transfer) (c.list()).get(0);

                ItemReturn itemReturn = new ItemReturn();
                itemReturn.setdFlag(1);
                itemReturn.setTransfer(transfer);
                itemReturn.setQuantity(qty);

                if (qty == transfer.getRemainingQtyToReturn()) {
                    transfer.setStatus(Transfer.STATUS_RETURNED_ALL);
                }
                transfer.setRemainingQtyToReturn(transfer.getRemainingQtyToReturn() - qty);
                transfer.setLastModifiedDate(new Date());
                // add return number
                itemReturn.setReturnNumber(returnNumber);
                itemReturn.setAddedDate(new Date());
                itemReturn.setReturnItemCondition(damageStatus);

                Item itemOld = transfer.getItem();
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

                s.update(transfer);
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
