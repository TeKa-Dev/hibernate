package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    static UserService service = new UserServiceImpl();

    public static void main(String[] args) {

        service.createUsersTable();
        service.saveUser("Kolya", "Ivanov", (byte) 15);
        showInfoByIndex(0);
        service.saveUser("Sveta", "Sidorova", (byte) 20);
        showInfoByIndex(1);
        service.saveUser("Vasya", "Petrov", (byte) 30);
        showInfoByIndex(2);
        service.saveUser("Katya", "Smirnova", (byte) 25);
        showInfoByIndex(3);

        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();

        Util.getSessionFactory().close();
    }

    static void showInfoByIndex(int i) {
        System.out.println("User с именем – "
                + service.getAllUsers().get(i).getName()
                + " добавлен в базу данных");
    }
}
