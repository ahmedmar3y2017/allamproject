package sample.app.Transactions.UserDao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import sample.app.Entities.Users;

import java.util.Iterator;
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


        session.beginTransaction();
        session.update(String.valueOf(id), Users);

        session.getTransaction().commit();
        Users c = (Users) session.get(Users.class, id);
        session.close();

        return c;


    }


    public static int SelectUsers(String phone, String password) {

        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Users.class);
        // To get total salary.
        System.out.println(phone);
        cr.add(Restrictions.eq("phone", phone));
        cr.add(Restrictions.eq("password", password));
        List employees = cr.list();
        int idEmployee = 0;

//        System.out.println("" + totalSalary.get(0));
        for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
            Users employee = (Users) iterator.next();
            System.out.print("First Name: " + employee.getName());
            System.out.print("  Last Name: " + employee.getId());
            idEmployee = employee.getId();
        }

        return idEmployee;

    }

    public static List<Users> SelectAllUsers() {

        Session session = sessionFactory.openSession();
        List<Users> Userses = session.createCriteria(Users.class).setCacheable(true)
                .setCacheRegion("SelectAllUsers.cache").list();

        session.close();

        return Userses;

    }


    public static Users UpdatePassword(int idEmployee, String s) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        Query query = session.createQuery("update Users as us set us.password=:pass where us.id=:id");
        query.setParameter("pass", s);
        query.setParameter("id", idEmployee);
        query.executeUpdate();

        session.getTransaction().commit();
        Users users = session.get(Users.class, idEmployee);

        session.close();


        return users;
    }

    public static Users SelectUserById(int idEmployee) {

        Session session = sessionFactory.openSession();
        Users users = session.get(Users.class, idEmployee);

        session.close();

        return users;


    }

    public static Users UpdatePhone(int idEmployee, String s) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        Query query = session.createQuery("update Users as us set us.phone=:phone where us.id=:id");
        query.setParameter("phone", s);
        query.setParameter("id", idEmployee);
        query.executeUpdate();

        session.getTransaction().commit();
        Users users = session.get(Users.class, idEmployee);

        session.close();


        return users;


    }

    public static Users UpdateActive(int id) {


        Session session = sessionFactory.openSession();
        session.beginTransaction();


        Query query = session.createQuery("update Users as us set us.isActive=false where us.id=:id");
        query.setParameter("id", id);

        query.executeUpdate();

        session.getTransaction().commit();
        Users users = session.get(Users.class, id);

        session.close();
        return users;


    }
}
