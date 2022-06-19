package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
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
        try (ResultSet allSet = Util.getStatement().executeQuery("select * from users")) {
            List<User> allUsers = new ArrayList<>();

            while (allSet.next()) {
                User user = new User();
                user.setId(allSet.getLong("id"));
                user.setName(allSet.getString("name"));
                user.setLastName(allSet.getString("last_name"));
                user.setAge(allSet.getByte("age"));
                allUsers.add(user);
            }
            return allUsers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private static void offerQuery(String sql) {
            try {
                Util.getStatement().execute(sql);
            } catch (SQLException e) {
                // ignore
            }
        }
}
