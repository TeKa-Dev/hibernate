package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
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
        return null;
    }

    private void offerQuery(String sql) {

        Transaction tran = null;
        try (Session session = Util.getSession()) {
             tran = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tran.commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
        }


//        try (Session session = Util.getSession()) {
//            session.createNativeQuery(sql);
//        }
//        catch (HibernateException e) {
//            // ignore
//        }

    }
}
