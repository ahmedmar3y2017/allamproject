package sample.app.Transactions.ScreenplusDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.ScreenPlus;

import java.util.List;

public class screenPlusDao {


    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static ScreenPlus SaveScreenPlus(ScreenPlus ScreenPlus) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(ScreenPlus);

        session.getTransaction().commit();

        session.close();

        return ScreenPlus;
    }

    public static ScreenPlus UpdateScreenPlus(int id, ScreenPlus ScreenPlus) {

        Session session = sessionFactory.openSession();
        if (SelectScreenPlus(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), ScreenPlus);

            session.getTransaction().commit();
            sample.app.Entities.ScreenPlus c = (sample.app.Entities.ScreenPlus) session.get(sample.app.Entities.ScreenPlus.class, id);
            session.close();

            return c;
        }

    }

    public static ScreenPlus DeleteScreenPlus(int id) {
        Session session = sessionFactory.openSession();

        ScreenPlus c = SelectScreenPlus(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            ScreenPlus s1 = (ScreenPlus) session.get(ScreenPlus.class, id);
            session.close();

            return s1;
        }
    }

    public static ScreenPlus SelectScreenPlus(int id) {

        Session session = sessionFactory.openSession();
        ScreenPlus ScreenPlus = (sample.app.Entities.ScreenPlus) session.get(sample.app.Entities.ScreenPlus.class, id);

        if (ScreenPlus == null) {
            session.close();

            return null;
        }
        session.close();

        return ScreenPlus;

    }

    public static List<ScreenPlus> SelectAllScreenPlus() {

        Session session = sessionFactory.openSession();
        List<ScreenPlus> ScreenPluses = session.createCriteria(ScreenPlus.class).setCacheable(true)
                .setCacheRegion("SelectAllScreenPlus.cache").list();

        session.close();

        return ScreenPluses;

    }

    public static List<ScreenPlus> SelectAllToday() {

        Session session = sessionFactory.openSession();
        List<ScreenPlus> screenPluses = session.createQuery("from ScreenPlus as main where main.date=current_date ").setCacheable(true)
                .setCacheRegion("SelectAllMaintableToday.cache").list();

        session.close();
        return screenPluses;


    }
}
