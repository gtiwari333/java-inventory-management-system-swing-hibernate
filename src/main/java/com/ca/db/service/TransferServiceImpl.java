package com.ca.db.service;

import com.ca.db.model.*;
import com.ca.ui.panels.ItemReceiverPanel.ReceiverType;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TransferServiceImpl extends BaseDAO {

    public TransferServiceImpl() throws Exception {
        super();
    }

    public static final void deleteTransfer(int transferId) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {

            Criteria c = s.createCriteria(Transfer.class);
            c.add(Restrictions.eq("id", transferId));
            Transfer transfer = (Transfer) (c.list()).get(0);

            if (transfer.getQuantity() != transfer.getRemainingQtyToReturn()) {
                throw new Exception(" This cannot be modified.");
            }

            Criteria cI = s.createCriteria(Item.class);
            cI.add(Restrictions.eq("id", transfer.getItem().getId()));
            Item item = (Item) (cI.list()).get(0);

            // qty in stock, add previous qty (since it is deleted)
            item.setQuantity(item.getQuantity() + transfer.getQuantity());
            transfer.setRemainingQtyToReturn(transfer.getRemainingQtyToReturn() - transfer.getQuantity());

            transfer.setdFlag(0);

            s.update(item);
            s.update(transfer);

            tx.commit();
        } catch (Exception er) {
            tx.rollback();
            er.printStackTrace();
            throw new Exception("Item transfer delete failed " + er.getMessage());
        }
    }

    public static final void updateTransfer(int transferId, int qty, Date transferDate, ReceiverType type, int id, String transferPanaNumber, String requestNumber,
                                            int hastantaranStatus) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {

            Criteria c = s.createCriteria(Transfer.class);
            c.add(Restrictions.eq("id", transferId));
            Transfer transfer = (Transfer) (c.list()).get(0);

            if (transfer.getQuantity() != transfer.getRemainingQtyToReturn()) {
                throw new Exception(" This cannot be modified.");
            }

            Criteria cI = s.createCriteria(Item.class);
            cI.add(Restrictions.eq("id", transfer.getItem().getId()));
            Item item = (Item) (cI.list()).get(0);

            // qty in stock, add previous qty and minus new one
            item.setQuantity(item.getQuantity() + transfer.getQuantity() - qty);

            transfer.setQuantity(qty);
            transfer.setRemainingQtyToReturn(transfer.getRemainingQtyToReturn() - transfer.getQuantity() + qty);

            transfer.setTransferPanaNumber(transferPanaNumber);
            transfer.setTransferRequestNumber(requestNumber);
            transfer.setHastantaranReceivedStatus(hastantaranStatus);
            item.setLastModifiedDate(new Date());
            transfer.setTransferDate(transferDate);

            switch (type) {
                case OFFICIAL:
                    transfer.setTransferType(Transfer.OFFICIAL);

                    Criteria cb = s.createCriteria(BranchOffice.class);
                    cb.add(Restrictions.eq("id", id));
                    BranchOffice br = (BranchOffice) (cb.list()).get(0);

                    transfer.setBranchOffice(br);
                    break;
                case PERSONNAL:
                    transfer.setTransferType(Transfer.PERSONNAL);

                    Criteria cp = s.createCriteria(Person.class);
                    cp.add(Restrictions.eq("id", id));
                    Person p = (Person) (cp.list()).get(0);

                    transfer.setPerson(p);
                    break;
                default:
                    transfer.setTransferType(Transfer.LILAM);
                    break;
            }

            s.update(item);
            s.update(transfer);

            tx.commit();
        } catch (Exception er) {
            tx.rollback();
            er.printStackTrace();
            throw new Exception("Item transfer update failed " + er.getMessage());
        }
    }

    public static final void saveTransfer(Map<Integer, Integer> cartMap, Date transferDate, ReceiverType type, int id, String transferPanaNumber, String requestNumber)
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
                        transfer.setTransferType(Transfer.OFFICIAL);

                        Criteria cb = s.createCriteria(BranchOffice.class);
                        cb.add(Restrictions.eq("id", id));
                        BranchOffice br = (BranchOffice) (cb.list()).get(0);

                        transfer.setBranchOffice(br);
                        break;
                    case PERSONNAL:
                        transfer.setTransferType(Transfer.PERSONNAL);

                        Criteria cp = s.createCriteria(Person.class);
                        cp.add(Restrictions.eq("id", id));
                        Person p = (Person) (cp.list()).get(0);

                        transfer.setPerson(p);
                        break;
                    default:
                        transfer.setTransferType(Transfer.LILAM);
                        break;
                }
                transfer.setdFlag(1);
                transfer.setStatus(Transfer.STATUS_NOT_RETURNED);
                transfer.setItem(item);
                transfer.setQuantity(qty);
                transfer.setRate(item.getRate());
                transfer.setRemainingQtyToReturn(qty);
                transfer.setTransferPanaNumber(transferPanaNumber);
                transfer.setTransferRequestNumber(requestNumber);
                transfer.setHastantaranReceivedStatus(Transfer.HASTANTARAN_NOT_RECEIVED);
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

    public static final List allTransferItemQuery(String itemName, int categoryId, int selectedReceiverType, int receiverId, int returnedStatus,
                                                  int hastantaranStatus, String transferNumber, String requestNumber, Date fromDate, Date toDate) throws Exception {
        Criteria c = getSession().createCriteria(Transfer.class);
        // c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));

        if (!StringUtils.isEmpty(transferNumber)) {
            c.add(Restrictions.eq("transferPanaNumber", transferNumber));
        }

        if (!StringUtils.isEmpty(requestNumber)) {
            c.add(Restrictions.eq("requestNumber", requestNumber));
        }
        if (selectedReceiverType > 0) {
            c.add(Restrictions.eq("transferType", selectedReceiverType));
        }
        // System.out.println(receiverId + ">> receiverId");
        if (returnedStatus >= 0) {
            c.add(Restrictions.eq("status", returnedStatus));
        }
        if (hastantaranStatus >= 0) {
            c.add(Restrictions.eq("hastantaranReceivedStatus", hastantaranStatus));
        }
        System.out.println(selectedReceiverType + ">> selectedReceiverType");
        if (receiverId > 0) {
            if (selectedReceiverType == Transfer.PERSONNAL) {

                c.setFetchMode("person", FetchMode.JOIN);
                c.add(Restrictions.eq("person.id", receiverId));

            } else if (selectedReceiverType == Transfer.OFFICIAL) {
                c.setFetchMode("branchOffice", FetchMode.JOIN);
                c.add(Restrictions.eq("branchOffice.id", receiverId));
            }
            // if any other
        }

        if (!StringUtils.isEmpty(itemName)) {
            c.createAlias("item", "it");
            c.add(Restrictions.ilike("it.name", "%" + itemName.toLowerCase() + "%", MatchMode.ANYWHERE));
        }

        if (categoryId > 0) {
            c.createAlias("item.category", "cat");
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

    /**
     * accessed by ItemReturnPanel
     */
    public static final List notReturnedTransferItemQuery(String itemName, int categoryId, int selectedReceiverType, int receiverId, int returnedStatus,
                                                          int hastantaranStatus, String transferNumber, String requestNumber, Date fromDate, Date toDate) throws Exception {
        Criteria c = getSession().createCriteria(Transfer.class);
        c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.gt("remainingQtyToReturn", 0));
        c.add(Restrictions.ne("transferType", Transfer.LILAM));

        if (!StringUtils.isEmpty(transferNumber)) {
            c.add(Restrictions.eq("transferPanaNumber", transferNumber));
        }

        if (!StringUtils.isEmpty(requestNumber)) {
            c.add(Restrictions.eq("requestNumber", requestNumber));
        }
        if (selectedReceiverType > 0) {
            c.add(Restrictions.eq("transferType", selectedReceiverType));
        }

        if (returnedStatus >= 0) {
            c.add(Restrictions.eq("status", returnedStatus));
        }
        if (hastantaranStatus >= 0) {
            c.add(Restrictions.eq("hastantaranReceivedStatus", hastantaranStatus));
        }
        if (receiverId > 0) {
            if (selectedReceiverType == Transfer.PERSONNAL) {

                c.setFetchMode("person", FetchMode.JOIN);
                c.add(Restrictions.eq("person.id", receiverId));

            } else if (selectedReceiverType == Transfer.OFFICIAL) {
                c.setFetchMode("branchOffice", FetchMode.JOIN);
                c.add(Restrictions.eq("branchOffice.id", receiverId));
            }
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
