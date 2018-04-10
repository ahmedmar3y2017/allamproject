package sample.app.Transactions.SafeDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.Safe;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class safeDao {

    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static Safe SaveSafe(Safe Safe) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(Safe);

        session.getTransaction().commit();

        session.close();

        return Safe;
    }

    public static Safe UpdateSafe(int id, Safe Safe) {

        Session session = sessionFactory.openSession();
        if (SelectSafe(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), Safe);

            session.getTransaction().commit();
            Safe c = (Safe) session.get(Safe.class, id);
            session.close();

            return c;
        }

    }

    public static Safe DeleteSafe(int id) {
        Session session = sessionFactory.openSession();

        Safe c = SelectSafe(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            Safe s1 = (Safe) session.get(Safe.class, id);
            session.close();

            return s1;
        }
    }

    public static Safe SelectSafe(int id) {

        Session session = sessionFactory.openSession();
        Safe Safe = (Safe) session.get(Safe.class, id);

        if (Safe == null) {
            session.close();

            return null;
        }
        session.close();

        return Safe;

    }

    public static List<Safe> SelectAllSafe() {

        Session session = sessionFactory.openSession();
        List<Safe> Safees = session.createCriteria(Safe.class).setCacheable(true)
                .setCacheRegion("SelectAllSafe.cache").list();

        session.close();

        return Safees;

    }

}
