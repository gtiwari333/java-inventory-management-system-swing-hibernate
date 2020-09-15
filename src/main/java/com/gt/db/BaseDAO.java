package com.gt.db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * abstract so no instantiation Abstracts out CRUD
 *
 * @author GT
 */
public abstract class BaseDAO extends SessionUtils {
    private Session session;
    private Transaction tx;

    public BaseDAO() {
        get();
    }

    public final int runQuery(String query) throws Exception {
        Query q = session.createQuery(query);
        return runQuery(q);
    }

    public final int runQuery(Query q) throws Exception {
        int ret = -1;
        try {
            startOperation();
            ret = q.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return ret;
    }

    public final List runReadQuery(Query q) throws Exception {
        List list;
        try {
            startOperation();
            list = q.list();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return list;
    }

    public final List runReadQuery(String query) throws Exception {
        Query q = session.createQuery(query);
        return runReadQuery(q);
    }

    public void saveOrUpdate(Object obj) throws Exception {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
    }

    public final void delete(Object obj) throws Exception {
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
    }

    public final Object find(Class clazz, Long id) throws Exception {
        Object obj;
        try {
            startOperation();
            obj = session.load(clazz, id);
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        }
        return obj;
    }

    public final List findAll(Class clazz) throws Exception {
        List objects;
        try {
            // startOperation();
            session = getSession();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return objects;
    }

    public final void handleException(Exception e) {
        System.out.println("Transaction rollback due to " + e.getMessage());
        rollback(tx);

    }

    public final void startOperation() {
        session = getSession();
        tx = session.beginTransaction();
    }
}
