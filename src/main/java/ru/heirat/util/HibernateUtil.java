package ru.heirat.util;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import java.util.logging.Level;
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            System.out.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() { getSessionFactory().close(); }
}
