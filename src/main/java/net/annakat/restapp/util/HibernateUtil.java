package net.annakat.restapp.util;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.UFile;
import net.annakat.restapp.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(User.class)
                    .addAnnotatedClass(Event.class).addAnnotatedClass(UFile.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session openSession() {
        return getSessionFactory().openSession();
    }


}

