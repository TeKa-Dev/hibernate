package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

/*
В методе main класса Main должны происходить следующие операции:

 1. Создание таблицы User(ов)
 2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль
    ( User с именем – name добавлен в базу данных )
 3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 4. Очистка таблицы User(ов)
 5. Удаление таблицы
 */
public class Main {
        static UserService service = new UserServiceImpl();

    public static void main(String[] args) {
        service.createUsersTable();
//        service.saveUser("Kolya", "Ivanov", (byte) 15);
//        showInfoByIndex(0);
//        service.saveUser("Sveta", "Sidorova", (byte) 20);
//        showInfoByIndex(1);
//        service.saveUser("Vasya", "Petrov", (byte) 30);
//        showInfoByIndex(2);
//        service.saveUser("Katya", "Smirnova", (byte) 25);
//        showInfoByIndex(3);
//
//        System.out.println(service.getAllUsers());
//        service.cleanUsersTable();
        service.dropUsersTable();
    }
    static void showInfoByIndex(int i) {
        System.out.println("User с именем – "
                + service.getAllUsers().get(i).getName()
                +" добавлен в базу данных");
    }
}
