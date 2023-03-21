package jm.task.core.jdbc.dao.java.jm.task.core.jdbc;

import jm.task.core.jdbc.dao.java.jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.dao.java.jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        try {
            userService.saveUser("Javi", "Kendo", (byte) 24);
            userService.saveUser("Lionel", "Messi", (byte) 35);
            userService.saveUser("Elon", "Musk", (byte) 51);
            userService.saveUser("Mark", "Zuckerberg", (byte) 38);

            System.out.println(userService.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}