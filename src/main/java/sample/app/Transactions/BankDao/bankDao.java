package sample.app.Transactions.BankDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.Bank;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class bankDao {


    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static Bank SaveBank(Bank Bank) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(Bank);

        session.getTransaction().commit();

        session.close();

        return Bank;
    }

    public static Bank UpdateBank(int id, Bank Bank) {

        Session session = sessionFactory.openSession();
        if (SelectBank(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), Bank);

            session.getTransaction().commit();
            Bank c = (Bank) session.get(Bank.class, id);
            session.close();

            return c;
        }

    }

    public static Bank DeleteBank(int id) {
        Session session = sessionFactory.openSession();

        Bank c = SelectBank(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            Bank s1 = (Bank) session.get(Bank.class, id);
            session.close();

            return s1;
        }
    }

    public static Bank SelectBank(int id) {

        Session session = sessionFactory.openSession();
        Bank Bank = (Bank) session.get(Bank.class, id);

        if (Bank == null) {
            session.close();

            return null;
        }
        session.close();

        return Bank;

    }

    public static List<Bank> SelectAllBank() {

        Session session = sessionFactory.openSession();
        List<Bank> Bankes = session.createCriteria(Bank.class).setCacheable(true)
                .setCacheRegion("SelectAllBank.cache").list();

        session.close();

        return Bankes;

    }


}
