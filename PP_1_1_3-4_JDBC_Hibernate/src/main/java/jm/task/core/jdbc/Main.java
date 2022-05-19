package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Todd", "Howard", (byte) 51);
        userService.saveUser("John", "Wick", (byte) 57);
        userService.saveUser("Phil", "Spencer", (byte) 54);
        userService.saveUser("Van", "Darkholme", (byte) 49);
        userService.getAllUsers().forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
