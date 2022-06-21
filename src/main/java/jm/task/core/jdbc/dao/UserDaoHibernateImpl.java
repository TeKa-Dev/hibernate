package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    public void createUsersTable() {
        offerQuery("create table users" +
                "(id bigint not null auto_increment," +
                "name varchar(15)," +
                "last_name varchar(15)," +
                "age tinyint," +
                "primary key (id))");
    }

    public void dropUsersTable() {
        offerQuery("drop table users");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            session.save(new User(name, lastName, age));
        } catch (Exception e) {
            // ignore
        }
    }

    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.createQuery("delete User where id = :id").setParameter("id", id);
        } catch (Exception e) {
            // ignore
        }
    }

    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.createQuery("delete User");
        } catch (Exception e) {
            // ignore
        }

    }

    public List<User> getAllUsers() {
        List<User> allUsers = Collections.emptyList();
        try (Session session = factory.openSession()) {
            allUsers = session.createQuery("from User").list();
        } catch (Exception e) {
            // ignore
        }
        return allUsers;
    }

    private void offerQuery(String sql) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            try {
                assert transaction != null;
                transaction.rollback();
            } catch (Exception ex) {
                // ignore
            }
        }
    }
}
