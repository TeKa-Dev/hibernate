package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USER = "user";
    private static final String PASS = "user";

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration()
                    .setProperty(Environment.URL, URL)
                    .setProperty(Environment.USER, USER)
                    .setProperty(Environment.PASS, PASS)
                    .addAnnotatedClass(User.class);

            ServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

//    Configuration config = new Configuration()
//            .addProperties(new Properties()
//                .put(Environment.URL, URL)
//                .put(Environment.USER, USER)
//                .put(Environment.PASS, PASS))

//      new MetadataSources( registry )
//        .addAnnotatedClass(User.class)
//        .buildMetadata()
//        .buildSessionFactory();
