package com.gt.db.utils;

import com.gt.db.BaseDAO;
import org.hibernate.Query;

import java.util.List;

/**
 * Commonly used db functions
 *
 * @author GT
 * <p>
 * Mar 3, 2012 com.gt.db.utils-DBUtils.java
 */
public class BaseDBUtils extends BaseDAO {

    /**
     * Status 1 = active - not deleted
     */

    public BaseDBUtils() {
        super();
    }

    public final List readAll(Class clazz) throws Exception {
        Query q = getSession().createQuery("from " + clazz.getName() + " where dflag=:status order by id desc");
        q.setInteger("status", 1);
        return super.runReadQuery(q);
    }

    public final List readAllNoStatus(Class clazz) throws Exception {
        Query q = getSession().createQuery("from " + clazz.getName() + "  order by id desc");
        return super.runReadQuery(q);
    }

    public final Object getById(Class clazz, int id) throws Exception {
        Query q = getSession().createQuery("from " + clazz.getName() + " where id=:id and dflag=:status order by id desc");
        q.setInteger("id", id);
        q.setInteger("status", 1);
        System.out.println(q.getQueryString() + " Reading ID " + id);
        return super.runReadQuery(q).get(0);

    }

    public final Object getByIdNoStatus(Class clazz, int id) throws Exception {
        Query q = getSession().createQuery("from " + clazz.getName() + " where id=:id order by id desc");
        q.setInteger("id", id);
        return super.runReadQuery(q).get(0);
    }

    public final int deleteById(Class clazz, int id) throws Exception {

        Query q = getSession().createQuery("update " + clazz.getName() + " set dflag=:status where id=:id");
        q.setInteger("status", 0);
        q.setInteger("id", id);
        return super.runQuery(q);
    }

    public final int deleteByIdPhysical(Class clazz, int id) throws Exception {
        Query q = getSession().createQuery("delete from " + clazz.getName() + " where id=:id");
        q.setInteger("id", id);
        return super.runQuery(q);

    }

    public final void saveOrUpdate(Object object) throws Exception {
        super.saveOrUpdate(object);

    }
}
