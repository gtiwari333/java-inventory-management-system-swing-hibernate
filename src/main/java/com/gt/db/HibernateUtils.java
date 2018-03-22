package com.gt.db;

import java.net.URLDecoder;
import java.util.Map;

import org.h2.tools.Server;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.gt.common.ResourceManager;
import com.gt.common.utils.Logger;

public class HibernateUtils {
    static Server server;
    private static SessionFactory sessionFactory;
    private static Session session;
    private static boolean isServerStarted = false;

    /**
     * Constructs a new Singleton SessionFactory
     *
     * @return
     * @throws HibernateException
     */
    public static SessionFactory buildSessionFactory() throws Exception {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    /**
     * Builds a SessionFactory, if it hasn't been already.
     */
    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            return configureSessionFactory();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public static Session getSession() throws Exception {
        getSessionFactory();
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                Logger.E("Couldn't close SessionFactory" + ignored.getMessage());
            }
        }
    }

    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                Logger.E("Couldn't close Session" + ignored.getMessage());
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            Logger.E("Couldn't rollback Transaction" + ignored.getMessage());
        }
    }

    public static boolean startServer() throws Exception {
        if (!isServerStarted) {
            server = Server.createTcpServer().start();

        }
        isServerStarted = true;
        return true;
    }

    public static boolean stopServer() {
        try {
            if (server != null) {
                server.stop();
            }
            isServerStarted = false;
            return true;
        } catch (Exception e) {
            System.out.println("Error during server start");
            return false;
        }
    }

    /**
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws Exception {

        Configuration configuration = new Configuration();

        configuration.configure("/hibernate.cfg.xml");

        Map<String, String> map = ResourceManager.readMap(ResourceManager.configMapFile, true);
        System.out.println("from hibernate config.xml " + map.get("connectionurl"));

        // current path
        String path = HibernateUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");

        System.out.println("Running from " + decodedPath);
        System.out.println(configuration.getProperty("hibernate.connection.driver_class"));

        sessionFactory = configuration.configure().buildSessionFactory();

        return sessionFactory;
    }

}