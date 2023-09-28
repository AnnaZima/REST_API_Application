package net.annakat.restapp.util;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.File;
import net.annakat.restapp.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;


public class HibernateUtil {
   private static SessionFactory sessionFactory;

   public static SessionFactory getSessionFactory() {
       if(sessionFactory == null) {
           try {
               Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
               sessionFactory = configuration.addAnnotatedClass(Event.class)
                       .addAnnotatedClass(File.class)
                       .addAnnotatedClass(User.class).buildSessionFactory();

           } catch (Exception e) {
               System.out.println(Arrays.toString(e.getStackTrace()));
           }
       }
      return sessionFactory;
   }



}
