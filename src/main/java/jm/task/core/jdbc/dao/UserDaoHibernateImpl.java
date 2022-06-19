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
        offerQuery("insert into users (name, last_name, age)" +
                "values (\'" + name + "\', \'"+ lastName + "\', "+ age + ");");
    }

    public void removeUserById(long id) {
        offerQuery("delete from users where id = " + id);
    }

    public void cleanUsersTable() {
        offerQuery("delete from users");
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
            if (transaction != null) {
                transaction.rollback();
            }
            // ignore
        }
    }
}
