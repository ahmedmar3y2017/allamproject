package sample.app.Transactions.UserDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.Users;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class userDao {

    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static Users SaveUsers(Users Users) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(Users);

        session.getTransaction().commit();

        session.close();

        return Users;
    }

    public static Users UpdateUsers(int id, Users Users) {

        Session session = sessionFactory.openSession();
        if (SelectUsers(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), Users);

            session.getTransaction().commit();
            Users c = (Users) session.get(Users.class, id);
            session.close();

            return c;
        }

    }

    public static Users DeleteUsers(int id) {
        Session session = sessionFactory.openSession();

        Users c = SelectUsers(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            Users s1 = (Users) session.get(Users.class, id);
            session.close();

            return s1;
        }
    }

    public static Users SelectUsers(int id) {

        Session session = sessionFactory.openSession();
        Users Users = (Users) session.get(Users.class, id);

        if (Users == null) {
            session.close();

            return null;
        }
        session.close();

        return Users;

    }

    public static List<Users> SelectAllUsers() {

        Session session = sessionFactory.openSession();
        List<Users> Userses = session.createCriteria(Users.class).setCacheable(true)
                .setCacheRegion("SelectAllUsers.cache").list();

        session.close();

        return Userses;

    }


}
