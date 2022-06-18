package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DRIVER;

public class Util {
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private static final String USER = "user";
    private static final String PASS = "user";

    private static Statement statement;
    private static Session session;


    public static Statement getStatement() {
        if (statement == null) {
            try {
                statement = DriverManager
                        .getConnection(URL, USER, PASS)
                        .createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

    public static Session getSession() {
        if (session == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASS);
                properties.put(DRIVER, DRIVER);
                properties.put(Environment.DIALECT, DIALECT);
                properties.put(Environment.SHOW_SQL,"true");

                Configuration configuration = new Configuration()
                        .setProperties(properties)
                        .addAnnotatedClass(User.class);

                ServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                SessionFactory factory = configuration.buildSessionFactory(registry);
                session = factory.openSession();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return session;
    }
}
