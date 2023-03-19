package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Javi", "Kendo", (byte) 24);
        userService.saveUser("Lionel", "Messi", (byte) 35);
        userService.saveUser("Elon", "Musk", (byte) 51);
        userService.saveUser("Mark", "Zuckerberg", (byte) 38);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}