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

public class NikasaServiceImpl extends BaseDAO {

    public NikasaServiceImpl() throws Exception {
        super();
    }

    public void deleteNikasa(int nikasaId) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {

            Criteria c = s.createCriteria(Nikasa.class);
            c.add(Restrictions.eq("id", nikasaId));
            Nikasa nikasa = (Nikasa) (c.list()).get(0);

            if (nikasa.getQuantity() != nikasa.getRemainingQtyToReturn()) {
                throw new Exception(" This cannot be modified.");
            }

            Criteria cI = s.createCriteria(Item.class);
            cI.add(Restrictions.eq("id", nikasa.getItem().getId()));
            Item item = (Item) (cI.list()).get(0);

            // qty in stock, add previous qty (since it is deleted)
            item.setQuantity(item.getQuantity() + nikasa.getQuantity());
            nikasa.setRemainingQtyToReturn(nikasa.getRemainingQtyToReturn() - nikasa.getQuantity());

            nikasa.setdFlag(0);

            s.update(item);
            s.update(nikasa);

            tx.commit();
        } catch (Exception er) {
            tx.rollback();
            er.printStackTrace();
            throw new Exception("Item nikasa delete failed " + er.getMessage());
        }
    }

    public void updateNikasa(int nikasaId, int qty, Date nikasaDate, ReceiverType type, int id, String nikasaPanaNumber, String requestNumber,
                             int hastantaranStatus) throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {

            Criteria c = s.createCriteria(Nikasa.class);
            c.add(Restrictions.eq("id", nikasaId));
            Nikasa nikasa = (Nikasa) (c.list()).get(0);

            if (nikasa.getQuantity() != nikasa.getRemainingQtyToReturn()) {
                throw new Exception(" This cannot be modified.");
            }

            Criteria cI = s.createCriteria(Item.class);
            cI.add(Restrictions.eq("id", nikasa.getItem().getId()));
            Item item = (Item) (cI.list()).get(0);

            // qty in stock, add previous qty and minus new one
            item.setQuantity(item.getQuantity() + nikasa.getQuantity() - qty);

            nikasa.setQuantity(qty);
            nikasa.setRemainingQtyToReturn(nikasa.getRemainingQtyToReturn() - nikasa.getQuantity() + qty);

            nikasa.setNikasaPanaNumber(nikasaPanaNumber);
            nikasa.setNikasaRequestNumber(requestNumber);
            nikasa.setHastantaranReceivedStatus(hastantaranStatus);
            item.setLastModifiedDate(new Date());
            nikasa.setNikasaDate(nikasaDate);

            if (type == ReceiverType.OFFICIAL) {
                nikasa.setNikasaType(Nikasa.OFFICIAL);

                Criteria cb = s.createCriteria(BranchOffice.class);
                cb.add(Restrictions.eq("id", id));
                BranchOffice br = (BranchOffice) (cb.list()).get(0);

                nikasa.setBranchOffice(br);
            } else if (type == ReceiverType.PERSONNAL) {
                nikasa.setNikasaType(Nikasa.PERSONNAL);

                Criteria cp = s.createCriteria(Person.class);
                cp.add(Restrictions.eq("id", id));
                Person p = (Person) (cp.list()).get(0);

                nikasa.setPerson(p);
            } else {
                nikasa.setNikasaType(Nikasa.LILAM);
            }

            s.update(item);
            s.update(nikasa);

            tx.commit();
        } catch (Exception er) {
            tx.rollback();
            er.printStackTrace();
            throw new Exception("Item nikasa update failed " + er.getMessage());
        }
    }

    public void saveNikasa(Map<Integer, Integer> cartMap, Date nikasaDate, ReceiverType type, int id, String nikasaPanaNumber, String requestNumber)
            throws Exception {
        Session s = getSession();
        Transaction tx = s.beginTransaction();

        for (Entry<Integer, Integer> entry : cartMap.entrySet()) {
            try {
                int itemId = entry.getKey();
                int qty = entry.getValue();

                // save nikasa entry
                Nikasa nikasa = new Nikasa();
                Criteria c = s.createCriteria(Item.class);
                c.add(Restrictions.eq("id", itemId));
                Item item = (Item) (c.list()).get(0);
                // qty in stock
                item.setQuantity(item.getQuantity() - qty);

                if (type == ReceiverType.OFFICIAL) {
                    nikasa.setNikasaType(Nikasa.OFFICIAL);

                    Criteria cb = s.createCriteria(BranchOffice.class);
                    cb.add(Restrictions.eq("id", id));
                    BranchOffice br = (BranchOffice) (cb.list()).get(0);

                    nikasa.setBranchOffice(br);
                } else if (type == ReceiverType.PERSONNAL) {
                    nikasa.setNikasaType(Nikasa.PERSONNAL);

                    Criteria cp = s.createCriteria(Person.class);
                    cp.add(Restrictions.eq("id", id));
                    Person p = (Person) (cp.list()).get(0);

                    nikasa.setPerson(p);
                } else {
                    nikasa.setNikasaType(Nikasa.LILAM);
                }
                nikasa.setdFlag(1);
                nikasa.setStatus(Nikasa.STATUS_NOT_RETURNED);
                nikasa.setItem(item);
                nikasa.setQuantity(qty);
                nikasa.setRate(item.getRate());
                nikasa.setRemainingQtyToReturn(qty);
                nikasa.setNikasaPanaNumber(nikasaPanaNumber);
                nikasa.setNikasaRequestNumber(requestNumber);
                nikasa.setHastantaranReceivedStatus(Nikasa.HASTANTARAN_NOT_RECEIVED);
                item.setLastModifiedDate(new Date());
                nikasa.setNikasaDate(nikasaDate);
                // save/update tables
                s.update(item);
                s.save(nikasa);

                tx.commit();
            } catch (Exception er) {
                tx.rollback();
                er.printStackTrace();
                throw new Exception("Item nikasa failed " + er.getMessage());
            }
        }
    }

    public List<Nikasa> allNikasaItemQuery(String itemName, int categoryId, int selectedReceiverType, int receiverId, int returnedStatus,
                                           int hastantaranStatus, String nikasaNumber, String requestNumber, Date fromDate, Date toDate) throws Exception {
        Criteria c = getSession().createCriteria(Nikasa.class);
        // c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));

        if (!StringUtils.isEmpty(nikasaNumber)) {
            c.add(Restrictions.eq("nikasaPanaNumber", nikasaNumber));
        }

        if (!StringUtils.isEmpty(requestNumber)) {
            c.add(Restrictions.eq("requestNumber", requestNumber));
        }
        if (selectedReceiverType > 0) {
            c.add(Restrictions.eq("nikasaType", selectedReceiverType));
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
            if (selectedReceiverType == Nikasa.PERSONNAL) {

                c.setFetchMode("person", FetchMode.JOIN);
                c.add(Restrictions.eq("person.id", receiverId));

            } else if (selectedReceiverType == Nikasa.OFFICIAL) {
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
            c.add(Restrictions.ge("nikasaDate", fromDate));
        }
        if (!DateTimeUtils.isEmpty(toDate)) {
            c.add(Restrictions.le("nikasaDate", toDate));
        }

        return c.list();
    }

    /**
     * accessed by ItemReturnPanel
     */
    public List<Nikasa> notReturnedNikasaItemQuery(String itemName, int categoryId, int selectedReceiverType, int receiverId, int returnedStatus,
                                                   int hastantaranStatus, String nikasaNumber, String requestNumber, Date fromDate, Date toDate) throws Exception {
        Criteria c = getSession().createCriteria(Nikasa.class);
        c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.gt("remainingQtyToReturn", 0));
        c.add(Restrictions.ne("nikasaType", Nikasa.LILAM));

        if (!StringUtils.isEmpty(nikasaNumber)) {
            c.add(Restrictions.eq("nikasaPanaNumber", nikasaNumber));
        }

        if (!StringUtils.isEmpty(requestNumber)) {
            c.add(Restrictions.eq("requestNumber", requestNumber));
        }
        if (selectedReceiverType > 0) {
            c.add(Restrictions.eq("nikasaType", selectedReceiverType));
        }

        if (returnedStatus >= 0) {
            c.add(Restrictions.eq("status", returnedStatus));
        }
        if (hastantaranStatus >= 0) {
            c.add(Restrictions.eq("hastantaranReceivedStatus", hastantaranStatus));
        }
        if (receiverId > 0) {
            if (selectedReceiverType == Nikasa.PERSONNAL) {

                c.setFetchMode("person", FetchMode.JOIN);
                c.add(Restrictions.eq("person.id", receiverId));

            } else if (selectedReceiverType == Nikasa.OFFICIAL) {
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
            c.add(Restrictions.ge("nikasaDate", fromDate));
        }
        if (!DateTimeUtils.isEmpty(toDate)) {
            c.add(Restrictions.le("nikasaDate", toDate));
        }

        return c.list();
    }

}
