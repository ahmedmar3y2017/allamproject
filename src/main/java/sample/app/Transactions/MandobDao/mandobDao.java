package sample.app.Transactions.MandobDao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.mandob;


import java.util.Date;
import java.util.List;

public class mandobDao {

    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static mandob Savemandob(mandob mandob) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(mandob);

        session.getTransaction().commit();

        session.close();

        return mandob;
    }

    public static mandob Updatemandob(int id, mandob mandob) {

        Session session = sessionFactory.openSession();
        if (Selectmandob(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), mandob);

            session.getTransaction().commit();
            sample.app.Entities.mandob c = (sample.app.Entities.mandob) session.get(sample.app.Entities.mandob.class, id);
            session.close();

            return c;
        }

    }

    public static mandob Deletemandob(int id) {
        Session session = sessionFactory.openSession();

        mandob c = Selectmandob(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            mandob s1 = (mandob) session.get(mandob.class, id);
            session.close();

            return s1;
        }
    }

    public static mandob Selectmandob(int id) {

        Session session = sessionFactory.openSession();
        mandob mandob = (sample.app.Entities.mandob) session.get(sample.app.Entities.mandob.class, id);

        if (mandob == null) {
            session.close();

            return null;
        }
        session.close();

        return mandob;

    }

    public static List<mandob> SelectAllmandob() {

        Session session = sessionFactory.openSession();
        List<mandob> mandobes = session.createCriteria(mandob.class).setCacheable(true)
                .setCacheRegion("SelectAllmandob.cache").list();

        session.close();

        return mandobes;

    }


    public static List<mandob> SelectAllToday() {

        Session session = sessionFactory.openSession();
        List<mandob> mandobs = session.createQuery("from mandob as main where main.date=current_date ").setCacheable(true)
                .setCacheRegion("SelectAllMandobToday.cache").list();

        session.close();
        return mandobs;


    }

    public static List<mandob> SelectAllmandobByDate(Date ddate) {

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from mandob as main where main.date=:da").setCacheable(true)
                .setCacheRegion("SelectAllMandobDate.cache");
        query.setParameter("da", ddate);
        List<mandob> mandobs = query.list();

        session.close();
        return mandobs;


    }
}
