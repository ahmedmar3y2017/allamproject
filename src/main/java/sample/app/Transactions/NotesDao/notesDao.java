package sample.app.Transactions.NotesDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sample.app.Entities.Notes;

import java.util.List;

/**
 * Created by ahmed mar3y on 10/04/2018.
 */
public class notesDao {


    static SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();

    public static Notes SaveNotes(Notes Notes) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(Notes);

        session.getTransaction().commit();

        session.close();

        return Notes;
    }

    public static Notes UpdateNotes(int id, Notes Notes) {

        Session session = sessionFactory.openSession();
        if (SelectNotes(id) == null) {
            session.close();
            return null;

        } else {


            session.beginTransaction();
            session.update(String.valueOf(id), Notes);

            session.getTransaction().commit();
            Notes c = (Notes) session.get(Notes.class, id);
            session.close();

            return c;
        }

    }

    public static Notes DeleteNotes(int id) {
        Session session = sessionFactory.openSession();

        Notes c = SelectNotes(id);
        if (c == null) {
            session.close();

            return null;

        } else {
            session.beginTransaction();
            session.delete(c);

            session.getTransaction().commit();
            Notes s1 = (Notes) session.get(Notes.class, id);
            session.close();

            return s1;
        }
    }

    public static Notes SelectNotes(int id) {

        Session session = sessionFactory.openSession();
        Notes Notes = (Notes) session.get(Notes.class, id);

        if (Notes == null) {
            session.close();

            return null;
        }
        session.close();

        return Notes;

    }

    public static List<Notes> SelectAllNotes() {

        Session session = sessionFactory.openSession();
        List<Notes> Noteses = session.createCriteria(Notes.class).setCacheable(true)
                .setCacheRegion("SelectAllNotes.cache").list();

        session.close();

        return Noteses;

    }


}
