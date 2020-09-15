package com.ca.db.service;

import com.ca.db.model.BranchOffice;
import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.Transfer;
import com.ca.ui.panels.ItemReceiverPanel.ReceiverType;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TransferServiceImpl extends BaseDAO {

    public TransferServiceImpl() {
        super();
    }

    public static void saveTransfer(Map<Integer, Integer> cartMap, Date transferDate, ReceiverType type, int id, String requestNumber)
            throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();

        for (Entry<Integer, Integer> entry : cartMap.entrySet()) {
            try {
                int itemId = entry.getKey();
                int qty = entry.getValue();

                // save transfer entry
                Transfer transfer = new Transfer();
                Criteria c = s.createCriteria(Item.class);
                c.add(Restrictions.eq("id", itemId));
                Item item = (Item) (c.list()).get(0);
                // qty in stock
                item.setQuantity(item.getQuantity() - qty);

                switch (type) {
                    case OFFICIAL:

                        Criteria cb = s.createCriteria(BranchOffice.class);
                        cb.add(Restrictions.eq("id", id));
                        BranchOffice br = (BranchOffice) (cb.list()).get(0);

                        transfer.setBranchOffice(br);
                        break;
                    default:
                        break;
                }
                transfer.setdFlag(1);
                transfer.setStatus(Transfer.STATUS_NOT_RETURNED);
                transfer.setItem(item);
                transfer.setQuantity(qty);
                transfer.setRate(item.getRate());
                transfer.setRemainingQtyToReturn(qty);
                transfer.setTransferRequestNumber(requestNumber);
                item.setLastModifiedDate(new Date());
                transfer.setTransferDate(transferDate);
                // save/update tables
                s.update(item);
                s.save(transfer);

                tx.commit();
            } catch (Exception er) {
                tx.rollback();
                er.printStackTrace();
                throw new Exception("Item transfer failed " + er.getMessage());
            }
        }
    }

    /**
     * accessed by ItemReturnPanel
     */
    public static List<Transfer> notReturnedTransferItemQuery(String itemName, int categoryId, int receiverId, int returnedStatus,
                                                    int receiveStatus, String requestNumber, Date fromDate, Date toDate) throws Exception {
        Criteria c = getSession().createCriteria(Transfer.class);
        c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.gt("remainingQtyToReturn", 0));

        if (!StringUtils.isEmpty(requestNumber)) {
            c.add(Restrictions.eq("requestNumber", requestNumber));
        }

        if (returnedStatus >= 0) {
            c.add(Restrictions.eq("status", returnedStatus));
        }
        if (receiveStatus >= 0) {
            c.add(Restrictions.eq("receiveStatus", receiveStatus));
        }
        if (receiverId > 0) {
            c.setFetchMode("branchOffice", FetchMode.JOIN);
            c.add(Restrictions.eq("branchOffice.id", receiverId));
            // if any other
        }
        if (!StringUtils.isEmpty(itemName)) {
            c.setFetchMode("item", FetchMode.JOIN);
            c.add(Restrictions.like("item.name", "%" + itemName + "%"));
        }
        c.createAlias("it.category", "cat");
        // only returnable
        c.add(Restrictions.eq("cat.categoryType", Category.TYPE_RETURNABLE));
        if (categoryId > 0) {
            // http://stackoverflow.com/questions/8726396/hibernate-criteria-join-with-3-tables
            c.add(Restrictions.eq("cat.id", categoryId));
        }

        if (!DateTimeUtils.isEmpty(fromDate)) {
            c.add(Restrictions.ge("transferDate", fromDate));
        }
        if (!DateTimeUtils.isEmpty(toDate)) {
            c.add(Restrictions.le("transferDate", toDate));
        }

        return c.list();
    }

}
