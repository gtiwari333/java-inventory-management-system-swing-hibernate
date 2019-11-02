package com.ca.db.service;

import com.gt.db.utils.BaseDBUtils;

import java.util.List;

public class DBUtils {

    private static synchronized BaseDBUtils getUtils() throws Exception {
        return new BaseDBUtils();
    }

    public static List readAll(Class clazz) throws Exception {
        return getUtils().readAll(clazz);
    }

    public static void saveOrUpdate(Object object) throws Exception {
        getUtils().saveOrUpdate(object);
    }

    public static List readAllNoStatus(Class clazz) throws Exception {
        return getUtils().readAllNoStatus(clazz);
    }

    public static Object getById(Class clazz, int id) throws Exception {
        return getUtils().getById(clazz, id);
    }

    public static Object getByIdNoStatus(Class clazz, int id) throws Exception {
        return getUtils().getByIdNoStatus(clazz, id);
    }

    public static int deleteById(Class clazz, int id) throws Exception {
        return getUtils().deleteById(clazz, id);
    }

    public static int deleteByIdPhysical(Class clazz, int id) throws Exception {
        return getUtils().deleteByIdPhysical(clazz, id);
    }
}
