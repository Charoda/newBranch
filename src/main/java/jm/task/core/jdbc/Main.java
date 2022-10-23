package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Akhmed","Guseynov", (byte) 24);
        userService.saveUser("Ruslan","Guseynov", (byte) 24);
        userService.saveUser("Zinaida","Okhlopkova", (byte) 19);
        userService.saveUser("Gazik","Gamzalov", (byte) 23);

        Iterator<User> iterator = userService.getAllUsers().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeSessionConnection();
    }
}
