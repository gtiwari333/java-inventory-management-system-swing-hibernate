package com.ca.db.service;

import com.ca.db.model.Item;
import com.ca.db.model.ItemReturn;
import com.ca.db.model.Transfer;
import com.ca.ui.report.LedgerReportBean;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;

import java.math.BigDecimal;
import java.util.*;

public class LedgerReportServiceImpl extends BaseDAO {

    public LedgerReportServiceImpl() throws Exception {
        super();
    }

    public static void main(String[] args) {
        try {
            LedgerReportServiceImpl lrs = new LedgerReportServiceImpl();
            lrs.getLedger(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(DateTimeUtils.getMySqlDate(new Date()));
    }

    public final List<LedgerReportBean> getLedger(String khataNumber, String panaNumber)
            throws Exception {
        Session s = getSession();

        StringBuilder sb = new StringBuilder();

        sb.append("select i.id as iid, nik.id, itr.id as nid from item i ");
        sb.append(" left join transfer nik on (i.ID = nik.item_id ");
        sb.append(" and  nik.REMAININGQTYTORETURN>0 ");
        sb.append(" and nik.dFlag <> '0' ");
        sb.append(" )");

        sb.append(" left join itemreturn itr on (nik.ID = itr.transfer_id ");
        sb.append(" and itr.dFlag  <>  '0' ");
        sb.append(" and itr.returnItemCondition in ( ");
        sb.append(ItemReturn.RETURN_ITEM_CONDITION_GOOD + ", ");
        sb.append(ItemReturn.RETURN_NEEDS_REPAIR);
        sb.append(" ) ");
        sb.append(" ) ");

        sb.append(" where 1=1");

        if (!StringUtils.isEmpty(khataNumber)) {
            sb.append(" and i.KHATANUMBER='").append(khataNumber).append("'");
        }
        sb.append(" and i.ACCOUNTTRANSFERSTATUS  <>  '" + Item.ACCOUNT_TRANSFERRED_TO_NEW + "' ");
        if (!StringUtils.isEmpty(panaNumber)) {
            sb.append(" and i.PANANUMBER ='").append(panaNumber).append("'");
        }
        sb.append(" and i.dFlag  <>  '0' ");

        sb.append(" order by i.KHATANUMBER, i.PANANUMBER, nik.transferDate, itr.addeddate ");
        System.out.println("reportQuery > " + sb.toString());

        NativeQuery sq = s.createSQLQuery(sb.toString());
        List result = sq.list();

        List<LedgerReportBean> rpbL = new ArrayList<>();
        Map<String, Integer> uniqueRows = new HashMap<>();

        for (Object o : result) {

            Object[] objects = (Object[]) o;
            Integer itemId = (Integer) objects[0];
            Integer nikId = (Integer) objects[1];
            Integer retId = (Integer) objects[2];
            LedgerReportBean rbItem = new LedgerReportBean();

            /**
             * Find Item from selected itemId
             */
            Criteria c = getSession().createCriteria(Item.class);
            c.add(Restrictions.eq("id", itemId));
            Item item = (Item) c.list().get(0);

            /**
             * Set parameters to report bean object
             */
            rbItem.setItemId(item.getId());
            rbItem.setDate(DateTimeUtils.getCvDateMMDDYYYY(item.getAddedDate()));
            rbItem.setGoodsName(item.getName());
            rbItem.setKhataPanaNumber(StringUtils.clean(item.getKhataNumber()) + "/" + StringUtils.clean(item.getPanaNumber()));
            rbItem.setEntryFormId(StringUtils.clean(item.getDakhilaNumber()));
            rbItem.setSupplier(StringUtils.clean(item.getVendor().getName()));
            rbItem.setSpecification(item.getSpeciifcationString());
            rbItem.setInQty(item.getOriginalQuantity() + "");
            rbItem.setUnitStock(item.getUnitsString().getValue());
            rbItem.setInRate(StringUtils.toString(item.getRate()));
            rbItem.setInTotal(getTotalPrice(item.getRate(), item.getQuantity()));
            rbItem.setNotes(" ");

            if (!rpbL.contains(rbItem)) {
                System.out.println("rbpl contains rbItem " + rbItem.getGoodsName());
                /**
                 * Do not repeat these, if same item already exists.
                 */

                int remQty = (item.getOriginalQuantity());
                rbItem.setRemQty(remQty + "");
                rbItem.setRemTot(getTotalPrice(item.getRate(), remQty));
                rbItem.setReqFormId("");
                rbItem.setTransferBranch(" ");
                rbItem.setNikQty("");
                rbItem.setNikRate("");
                rbItem.setNikTotal("");
                rbItem.setUnitTransfer("");

                rpbL.add(rbItem);

                // reset unique row set
                uniqueRows = new HashMap<>();

            }
            /**
             * Some item might not have transfer, check for this
             */
            if (nikId != null) {
                System.out.println("nikId != null");

                /**
                 * Find Transfer from transferId
                 */
                Criteria c2 = getSession().createCriteria(Transfer.class);
                c2.add(Restrictions.eq("id", nikId));
                Transfer nik = (Transfer) c2.list().get(0);

                LedgerReportBean rbTransfer = new LedgerReportBean();
                rbTransfer.setDate(DateTimeUtils.getCvDateMMDDYYYY(nik.getTransferDate()));
                /**
                 * Empty the item fields
                 */
                rbTransfer.setGoodsName("");
                rbTransfer.setEntryFormId("");
                rbTransfer.setInQty("");
                rbTransfer.setInRate("");
                rbTransfer.setInTotal("");
                rbTransfer.setSpecification("");
                rbTransfer.setSupplier("");
                rbTransfer.setUnitStock("");
                rbTransfer.setNotes("");
                rbTransfer.setKhataPanaNumber("");
                rbTransfer.setReqFormId(nik.getTransferRequestNumber());

                /**
                 * Set types
                 */
                getTransferString(nik, rbTransfer);
                rbTransfer.setNikQty(nik.getQuantity() + "");
                rbTransfer.setNikRate(StringUtils.toString(nik.getRate()));
                rbTransfer.setNikTotal(getTotalPrice(nik.getRate(), nik.getQuantity()));
                rbTransfer.setUnitTransfer(nik.getItem().getUnitsString().getValue());

                /**
                 * For Remaining quanity
                 */
                Integer rem = 0;
                if (uniqueRows.containsKey("I" + item.getId())) {
                    Integer prevQty = uniqueRows.get("I" + item.getId());
                    System.out.println("prev qty " + prevQty);
                    rem = prevQty - nik.getQuantity();
                    uniqueRows.put("I" + item.getId(), rem);
                    System.out.println("rem  " + rem);
                } else {
                    rem = item.getOriginalQuantity() - nik.getQuantity();
                    uniqueRows.put("I" + item.getId(), rem);
                }
                rbTransfer.setRemQty(rem + "");
                rbTransfer.setRemTot(getTotalPrice(item.getRate(), rem));

                rpbL.add(rbTransfer);

            }
            /**
             * Returned Item should be added there, check for this
             */
            if (retId != null) {
                System.out.println("retId != null");
                Criteria c2 = getSession().createCriteria(ItemReturn.class);
                c2.add(Restrictions.eq("id", retId));
                ItemReturn itemReturn = (ItemReturn) c2.list().get(0);

                LedgerReportBean rbReturn = new LedgerReportBean();
                rbReturn.setDate(DateTimeUtils.getCvDateMMDDYYYY(itemReturn.getAddedDate()));
                rbReturn.setGoodsName("");
                rbReturn.setEntryFormId("");
                rbReturn.setInQty(itemReturn.getQuantity() + "");
                rbReturn.setInRate(itemReturn.getTransfer().getItem().getRate() + "");
                rbReturn.setInTotal(getTotalPrice(itemReturn.getTransfer().getItem().getRate(), itemReturn.getQuantity()));
                rbReturn.setSpecification("");
//				rbReturn.setSupplier(nik.getTransfer().get);
                getTransferString(itemReturn.getTransfer(), rbReturn);
                rbReturn.setUnitStock("");
                rbReturn.setNotes("Returned Item");
                rbReturn.setKhataPanaNumber("");
                rbReturn.setReqFormId("");
                rbReturn.setSupplier("");
                rbReturn.setNikQty("");
                rbReturn.setNikRate("");
                rbReturn.setNikTotal("");
                rbReturn.setUnitTransfer("");

                int rem = 0;
                if (uniqueRows.containsKey("I" + item.getId())) {
                    Integer prevQty = uniqueRows.get("I" + item.getId());
                    System.out.println("prev qty " + prevQty);
                    rem = prevQty + itemReturn.getQuantity();
                    uniqueRows.put("I" + item.getId(), rem);
                    System.out.println("rem  " + rem);
                } else {
                    rem = item.getOriginalQuantity() + itemReturn.getQuantity();
                    uniqueRows.put("I" + item.getId(), rem);
                }
                rbReturn.setUnitStock(itemReturn.getTransfer().getItem().getUnitsString().getValue());
                rbReturn.setReqFormId(itemReturn.getReturnNumber());
                rbReturn.setRemQty(rem + "");
                rbReturn.setRemQty(rem + "");
                rbReturn.setRemTot(getTotalPrice(item.getRate(), rem));

                rpbL.add(rbReturn);
            }

        } // end loop on query results
        return rpbL;
    }

    private static void getTransferString(Transfer nik, LedgerReportBean ledgerReportBean) {
        System.out.println("Transfer Type  " + nik.getTransferType());
        switch (nik.getTransferType()) {
            case Transfer.LILAM:
                ledgerReportBean.setTransferBranch(" ");
                ledgerReportBean.setNotes("Lilam");
                break;
            case Transfer.OFFICIAL:
                ledgerReportBean.setTransferBranch(nik.getBranchOffice().getName());
                break;
            case Transfer.PERSONNAL:
                ledgerReportBean.setTransferBranch(nik.getPerson().getFirstName() + " " + nik.getPerson().getLastName());
                break;
        }
    }

    private static String getTotalPrice(BigDecimal rate, int qty) {
        if (rate != null)
            return rate.multiply(new BigDecimal(qty)).toString();
        else
            return "";
    }

}
