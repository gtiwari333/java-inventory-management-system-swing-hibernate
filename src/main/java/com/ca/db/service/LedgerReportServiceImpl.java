package com.ca.db.service;

import com.ca.db.model.Item;
import com.ca.db.model.ItemReturn;
import com.ca.db.model.Nikasa;
import com.ca.ui.report.ReportBean;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.*;

public class LedgerReportServiceImpl extends BaseDAO {

    public LedgerReportServiceImpl() throws Exception {
        super();
    }

    public static void main(String[] args) {
        try {
            LedgerReportServiceImpl lrs = new LedgerReportServiceImpl();
            lrs.getLedger(null, null, 0, 0, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(DateTimeUtils.getMySqlDate(new Date()));
    }

    public List<ReportBean> getLedger(String khataNumber, String panaNumber, int categoryId, int vendorId, Date fromDate, Date toDate)
            throws Exception {
        Session s = getSession();

        StringBuffer sb = new StringBuffer();

        sb.append("select i.id as iid,nik.id, itr.id as nid from item i ");
        sb.append("left join nikasa nik on (i.ID = nik.item_id");
        sb.append(" and  nik.REMAININGQTYTORETURN>0 ");
        sb.append(" and nik.dFlag is not '0' ");
        sb.append(")");

        sb.append("left join itemreturn itr on (nik.ID = itr.nikasa_id");
        sb.append(" and itr.dFlag is not '0' ");
        sb.append(" and itr.returnItemCondition in (");
        sb.append(ItemReturn.RETURN_ITEM_CONDITION_GOOD + ", ");
        sb.append(ItemReturn.RETURN_NEEDS_REPAIR);
        sb.append(")");
        sb.append(")");

        sb.append(" where 1=1");

        if (!StringUtils.isEmpty(khataNumber)) {
            sb.append(" and i.KHATANUMBER='" + khataNumber + "'");
        }
        sb.append(" and i.ACCOUNTTRANSFERSTATUS is not '" + Item.ACCOUNT_TRANSFERRED_TO_NEW + "'");
        if (!StringUtils.isEmpty(panaNumber)) {
            sb.append(" and i.PANANUMBER ='" + panaNumber + "'");
        }
        sb.append(" and i.dFlag is not '0' ");

        sb.append(" order by i.KHATANUMBER, i.PANANUMBER, nik.nikasaDate, itr.addeddate");
        System.out.println("reportQuery > " + sb.toString());

        SQLQuery sq = s.createSQLQuery(sb.toString());
        List result = sq.list();

        List<ReportBean> rpbL = new ArrayList<ReportBean>();
        Map<String, Integer> uniqueRows = new HashMap<String, Integer>();

        for (Object o : result) {

            Object[] objects = (Object[]) o;
            Integer itemId = (Integer) objects[0];
            Integer nikId = (Integer) objects[1];
            Integer retId = (Integer) objects[2];
            // System.out.println("nik id " + nikId + " itemId  " + itemId);
            ReportBean rbItem = new ReportBean();
            // System.out.println("obejcts size " + objects.length);

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
                rbItem.setNikasaBranch(" ");
                rbItem.setNikQty("");
                rbItem.setNikRate("");
                rbItem.setNikTotal("");
                rbItem.setUnitNikasa("");

                rpbL.add(rbItem);

                // reset unique row set
                uniqueRows = new HashMap<String, Integer>();

            }
            /**
             * Some item might not have nikasa, check for this
             */
            if (nikId != null) {
                System.out.println("nikId != null");

                /**
                 * Find Nikasa from nikasaId
                 */
                Criteria c2 = getSession().createCriteria(Nikasa.class);
                c2.add(Restrictions.eq("id", nikId));
                Nikasa nik = (Nikasa) c2.list().get(0);

                ReportBean rbNikasa = new ReportBean();
                rbNikasa.setDate(DateTimeUtils.getCvDateMMDDYYYY(nik.getNikasaDate()));
                /**
                 * Empty the item fields
                 */
                rbNikasa.setGoodsName("");
                rbNikasa.setEntryFormId("");
                rbNikasa.setInQty("");
                rbNikasa.setInRate("");
                rbNikasa.setInTotal("");
                rbNikasa.setSpecification("");
                rbNikasa.setSupplier("");
                rbNikasa.setUnitStock("");
                rbNikasa.setNotes("");
                rbNikasa.setKhataPanaNumber("");
                // System.out.println(item.toString());
                // System.out.println(nik.toString());
                rbNikasa.setReqFormId(nik.getNikasaRequestNumber());

                /**
                 * Set types
                 */
                getNikasaString(nik, rbNikasa);
                rbNikasa.setNikQty(nik.getQuantity() + "");
                rbNikasa.setNikRate(StringUtils.toString(nik.getRate()));
                rbNikasa.setNikTotal(getTotalPrice(nik.getRate(), nik.getQuantity()));
                rbNikasa.setUnitNikasa(nik.getItem().getUnitsString().getValue());

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
                rbNikasa.setRemQty(rem + "");
                rbNikasa.setRemTot(getTotalPrice(item.getRate(), rem));

                rpbL.add(rbNikasa);

            }
            /**
             * Returned Item should be added there, check for this
             */
            if (retId != null) {
                System.out.println("retId != null");
                Criteria c2 = getSession().createCriteria(ItemReturn.class);
                c2.add(Restrictions.eq("id", retId));
                ItemReturn itemReturn = (ItemReturn) c2.list().get(0);

                ReportBean rbReturn = new ReportBean();
                rbReturn.setDate(DateTimeUtils.getCvDateMMDDYYYY(itemReturn.getAddedDate()));
                rbReturn.setGoodsName("");
                rbReturn.setEntryFormId("");
                rbReturn.setInQty(itemReturn.getQuantity() + "");
                rbReturn.setInRate(itemReturn.getNikasa().getItem().getRate() + "");
                rbReturn.setInTotal(getTotalPrice(itemReturn.getNikasa().getItem().getRate(), itemReturn.getQuantity()));
                rbReturn.setSpecification("");
//				rbReturn.setSupplier(nik.getNikasa().get);
                getNikasaString(itemReturn.getNikasa(), rbReturn);
                rbReturn.setUnitStock("");
                rbReturn.setNotes("Returned Item");
                rbReturn.setKhataPanaNumber("");
                rbReturn.setReqFormId("");
                rbReturn.setSupplier("");
                rbReturn.setNikQty("");
                rbReturn.setNikRate("");
                rbReturn.setNikTotal("");
                rbReturn.setUnitNikasa("");

                Integer rem = 0;
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
                rbReturn.setUnitStock(itemReturn.getNikasa().getItem().getUnitsString().getValue());
                rbReturn.setReqFormId(itemReturn.getReturnNumber());
                rbReturn.setRemQty(rem + "");
                rbReturn.setRemQty(rem + "");
                rbReturn.setRemTot(getTotalPrice(item.getRate(), rem));

                rpbL.add(rbReturn);
            }

        } // end loop on query results
        return rpbL;
    }

    private void getNikasaString(Nikasa nik, ReportBean reportBean) {
        System.out.println("Nikasa Type  " + nik.getNikasaType());
        if (nik.getNikasaType() == Nikasa.LILAM) {
            reportBean.setNikasaBranch(" ");
            reportBean.setNotes("Lilam");
        } else if (nik.getNikasaType() == Nikasa.OFFICIAL) {
            reportBean.setNikasaBranch(nik.getBranchOffice().getName());
        } else if (nik.getNikasaType() == Nikasa.PERSONNAL) {
            reportBean.setNikasaBranch(nik.getPerson().getFirstName() + " " + nik.getPerson().getLastName());
        }
    }

    private String getTotalPrice(BigDecimal rate, int qty) {
        // System.out.println("LedgerReportServcieImpl.getTotalPrice()  " +
        // rate);
        if (rate != null) return rate.multiply(new BigDecimal(qty)).toString();
        else
            return "";
    }
    /**
     * hibernate join same column names SQLQuery sq =
     * getSession().createSQLQuery(
     * "select * from item i left join nikasa nik on (i.ID = nik.item_id) order by i.id, i.name"
     * ) .addEntity(Item.class).addEntity(Nikasa.class); List result =
     * sq.list();
     *
     * Iterator ite = result.iterator(); List<ReportBean> rpbL = new
     * ArrayList<ReportBean>(); for (Object o : result) { Object[] objects =
     * (Object[]) o; Item item = (Item) objects[0]; Nikasa nik = (Nikasa)
     * objects[1];
     */
}
