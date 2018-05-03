package sample.app.Transactions.BankAccountDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.BankAccount;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class bankAccountDao {

    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static BankAccount SaveBankAccount(BankAccount BankAccount) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(BankAccount);

        session.getTransaction().commit();

        session.close();

        return BankAccount;
    }

    public static BankAccount UpdateBankAccount(int id, BankAccount BankAccount) {

        Session session = sessionFactory.openSession();
        if (SelectBankAccount(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), BankAccount);

            session.getTransaction().commit();
//            BankAccount c = (BankAccount) session.get(BankAccount.class, id);
            session.close();
            BankAccount c = SelectBankAccount(id);

            return c;
        }

    }

    public static BankAccount DeleteBankAccount(int id) {
        Session session = sessionFactory.openSession();

        BankAccount c = SelectBankAccount(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            BankAccount s1 = (BankAccount) session.get(BankAccount.class, id);
            session.close();

            return s1;
        }
    }

    public static BankAccount SelectBankAccount(int id) {

        Session session = sessionFactory.openSession();
        BankAccount BankAccount = (BankAccount) session.get(BankAccount.class, id);

        if (BankAccount == null) {
            session.close();

            return null;
        }
        session.close();

        return BankAccount;

    }

    public static List<BankAccount> SelectAllBankAccount() {

        Session session = sessionFactory.openSession();
        List<BankAccount> BankAccountes = session.createCriteria(BankAccount.class).setCacheable(true)
                .setCacheRegion("SelectAllBankAccount.cache").list();

        session.close();

        return BankAccountes;

    }

}
