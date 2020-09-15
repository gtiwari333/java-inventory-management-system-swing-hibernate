package com.ca.db.service;

import com.ca.db.model.Item;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class ItemServiceImpl extends BaseDAO {

    public ItemServiceImpl() {
        super();
    }

    @SuppressWarnings("deprecation")
    /*
      used by item transfer entry and stock query
     */
    public static List<Item> itemStockQuery(String itemName, int categoryId, int vendorId, String rackNumber, Date fromDate, Date toDate, List<String> specs) {

        Criteria c = getSession().createCriteria(Item.class);
        // c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.gt("quantity", 0));
        /*
          read all items that has not been transferred and qty >0, the items
          with qty =0 is copied to new by setting transferred status Item.ACCOUNT_TRANSFERRED_TO_NEW
         */
        c.add(Restrictions.ne("accountTransferStatus", Item.ACCOUNT_TRANSFERRED_TO_NEW));

        if (!StringUtils.isEmpty(itemName)) {
            c.add(Restrictions.ilike("name", "%" + itemName.toLowerCase() + "%", MatchMode.ANYWHERE));
        }
        if (categoryId > 0) {
            c.setFetchMode("category", FetchMode.JOIN);
            c.add(Restrictions.eq("category.id", categoryId));
        }
        if (vendorId > 0) {
            c.setFetchMode("vendor", FetchMode.JOIN);
            c.add(Restrictions.eq("vendor.id", vendorId));
        }

        if (!StringUtils.isEmpty(rackNumber)) {
            c.add(Restrictions.like("rackno", StringUtils.clean(rackNumber)));
        }

        if (!DateTimeUtils.isEmpty(fromDate)) {
            c.add(Restrictions.ge("purchaseDate", fromDate));
        }
        if (!DateTimeUtils.isEmpty(toDate)) {
            c.add(Restrictions.le("purchaseDate", toDate));
        }
        // System.out.println("Specs size " + specs.size());
        if (specs != null && specs.size() > 0) {
            try {
                c.createAlias("specification", "sp", Criteria.LEFT_JOIN);
                if (!StringUtils.isEmpty(specs.get(0))) {
                    c.add(Restrictions.eq("sp.specification1", specs.get(0)));
                }
                if (!StringUtils.isEmpty(specs.get(1))) {
                    c.add(Restrictions.eq("sp.specification2", specs.get(1)));
                }
                if (!StringUtils.isEmpty(specs.get(2))) {
                    c.add(Restrictions.eq("sp.specification3", specs.get(2)));
                }
                if (!StringUtils.isEmpty(specs.get(3))) {
                    c.add(Restrictions.eq("sp.specification4", specs.get(3)));
                }
                if (!StringUtils.isEmpty(specs.get(4))) {
                    c.add(Restrictions.eq("sp.specification5", specs.get(4)));
                }
                if (!StringUtils.isEmpty(specs.get(5))) {
                    c.add(Restrictions.eq("sp.specification6", specs.get(5)));
                }
                if (!StringUtils.isEmpty(specs.get(6))) {
                    c.add(Restrictions.eq("sp.specification7", specs.get(6)));
                }
                if (!StringUtils.isEmpty(specs.get(7))) {
                    c.add(Restrictions.eq("sp.specification8", specs.get(7)));
                }
                if (!StringUtils.isEmpty(specs.get(8))) {
                    c.add(Restrictions.eq("sp.specification9", specs.get(8)));
                }
                if (!StringUtils.isEmpty(specs.get(9))) {
                    c.add(Restrictions.eq("sp.specification10", specs.get(9)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return c.list();
    }

    public static List<Item> getAddedItems() {
        Criteria c = getSession().createCriteria(Item.class);
        // c.createAlias("item", "it");
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.ne("accountTransferStatus", Item.ACCOUNT_TRANSFERRED_TO_NEW));
        c.add(Restrictions.eq("addedType", Item.ADD_TYPE_NEW_ENTRY));
        // TODO:fiscal year
        return c.list();
    }


}
