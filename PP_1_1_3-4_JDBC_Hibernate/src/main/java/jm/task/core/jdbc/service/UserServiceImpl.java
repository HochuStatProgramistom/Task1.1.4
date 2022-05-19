package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao service = new UserDaoHibernateImpl();

    public void createUsersTable() {
        service.createUsersTable();
    }

    public void dropUsersTable() {
        service.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        service.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        service.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    public void cleanUsersTable() {
        service.cleanUsersTable();
    }
}
