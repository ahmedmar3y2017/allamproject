package sample.app.Transactions.MainTableDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.Maintable;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class mainTableDao {


    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static Maintable SaveMaintable(Maintable Maintable) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(Maintable);

        session.getTransaction().commit();

        session.close();

        return Maintable;
    }

    public static Maintable UpdateMaintable(int id, Maintable Maintable) {

        Session session = sessionFactory.openSession();
        if (SelectMaintable(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), Maintable);

            session.getTransaction().commit();
            Maintable c = (Maintable) session.get(Maintable.class, id);
            session.close();

            return c;
        }

    }

    public static Maintable DeleteMaintable(int id) {
        Session session = sessionFactory.openSession();

        Maintable c = SelectMaintable(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            Maintable s1 = (Maintable) session.get(Maintable.class, id);
            session.close();

            return s1;
        }
    }

    public static Maintable SelectMaintable(int id) {

        Session session = sessionFactory.openSession();
        Maintable Maintable = (Maintable) session.get(Maintable.class, id);

        if (Maintable == null) {
            session.close();

            return null;
        }
        session.close();

        return Maintable;

    }

    public static List<Maintable> SelectAllMaintable() {

        Session session = sessionFactory.openSession();
        List<Maintable> Maintablees = session.createCriteria(Maintable.class).setCacheable(true)
                .setCacheRegion("SelectAllMaintable.cache").list();

        session.close();

        return Maintablees;

    }


    public static List<Maintable> SelectAllMaintableToday() {
        Session session = sessionFactory.openSession();
        List<Maintable> Maintablees = session.createQuery("from Maintable as main where main.date=current_date ").setCacheable(true)
                .setCacheRegion("SelectAllMaintableToday.cache").list();

        session.close();
        return Maintablees;

    }
}
