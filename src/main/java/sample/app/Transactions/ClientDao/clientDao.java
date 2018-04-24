package sample.app.Transactions.ClientDao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.Clients;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class clientDao {


    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static Clients SaveClients(Clients Clients) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(Clients);

        session.getTransaction().commit();

        session.close();

        return Clients;
    }

    public static Clients UpdateClients(int id, Clients Clients) {

        Session session = sessionFactory.openSession();
        if (SelectClients(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), Clients);

            session.getTransaction().commit();
            Clients c = (Clients) session.get(Clients.class, id);
            session.close();

            return c;
        }

    }

    public static Clients DeleteClients(int id) {
        Session session = sessionFactory.openSession();

        Clients c = SelectClients(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            Clients s1 = (Clients) session.get(Clients.class, id);
            session.close();

            return s1;
        }
    }

    public static Clients SelectClients(int id) {

        Session session = sessionFactory.openSession();
        Clients Clients = (Clients) session.get(Clients.class, id);

        if (Clients == null) {
            session.close();

            return null;
        }
        session.close();

        return Clients;

    }

    public static List<Clients> SelectAllClients() {

        Session session = sessionFactory.openSession();
        List<Clients> Clientses = session.createCriteria(Clients.class).setCacheable(true)
                .setCacheRegion("SelectAllClients.cache").list();

        session.close();

        return Clientses;

    }


    public static Clients checkClientByPhone(String phone) {
        Session session = sessionFactory.openSession();
        Query query = session.getNamedQuery("Clients.findByPhone").setCacheable(true)
                .setCacheRegion("checkClientByPhone.cache");
        query.setParameter("phone", phone);


        return (Clients) query.uniqueResult();
    }
}
