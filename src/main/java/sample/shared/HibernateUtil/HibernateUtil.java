/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shared.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import sample.app.Entities.*;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author ahmed mar3y
 */
public class HibernateUtil {


    //XML based configuration
    private static SessionFactory sessionFactory;


    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            // add classes Annotations
            configuration.addAnnotatedClass(Clients.class);
            configuration.addAnnotatedClass(Bank.class);
            configuration.addAnnotatedClass(BankAccount.class);
            configuration.addAnnotatedClass(Maintable.class);
            configuration.addAnnotatedClass(Notes.class);
            configuration.addAnnotatedClass(Safe.class);
            configuration.addAnnotatedClass(Users.class);
            configuration.addAnnotatedClass(ScreenPlus.class);
            configuration.addAnnotatedClass(mandob.class);

            System.out.println("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
