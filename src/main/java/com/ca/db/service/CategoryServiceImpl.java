package com.ca.db.service;

import com.ca.db.model.Category;
import com.ca.db.model.SubCategory;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

class CategoryServiceImpl extends BaseDAO {

    public CategoryServiceImpl() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    public static final boolean isCategoryExists(String categoryName) throws Exception {

        Criteria c = getSession().createCriteria(Category.class);
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.ilike("categoryName", categoryName.toLowerCase()));
        // c.add(Expression.eq("categoryName", categoryName).ignoreCase());
        List l = c.list();
        return l.size() > 0;

    }

    public static final boolean isSubCategoryExists(int categoryId, String subCategoryName) throws Exception {

        Criteria c = getSession().createCriteria(SubCategory.class);
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.eq("categoryId", categoryId));

        c.add(Restrictions.ilike("subCategoryName", subCategoryName.toLowerCase()));
        List l = c.list();
        return l.size() > 0;
    }

    public static final SubCategory getSubCategoryByname(int categoryId, String subCategoryName) throws Exception {
        Criteria c = getSession().createCriteria(SubCategory.class);
        c.add(Restrictions.eq("dFlag", 1));
        c.add(Restrictions.eq("categoryId", categoryId));

        c.add(Restrictions.ilike("subCategoryName", subCategoryName.toLowerCase()));
        List l = c.list();
        return (SubCategory) l.get(0);
    }
}
