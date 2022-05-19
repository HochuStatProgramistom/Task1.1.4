package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getSQLConnection();

    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            final String SQL_CREATE_TABLE = "CREATE TABLE Users"
                    + "("
                    + " ID INT NOT NULL AUTO_INCREMENT,"
                    + " NAME varchar(100) NOT NULL,"
                    + " LASTNAME VARCHAR(100) NOT NULL,"
                    + " AGE INT NOT NULL,"
                    + " PRIMARY KEY (ID)"
                    + ")";
            statement.execute(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            if (!e.getMessage().equals("Table 'users' already exists")) {
                System.err.format(e.getMessage());
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            final String SQL_DROP_TABLE = "DROP TABLE Users";
            statement.execute(SQL_DROP_TABLE);
        } catch (SQLException e) {
            if (!e.getMessage().equals("Unknown table 'task_1.users'")) {
                System.err.format(e.getMessage());
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SQL_SAVE_USER = "INSERT INTO Users (NAME, LASTNAME, AGE) VALUE(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.format(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        final String SQL_REMOVE_USER = "DELETE FROM Users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        final String SQL_GET_ALL = "SELECT * FROM Users";
        try (ResultSet resultSet = connection.createStatement().executeQuery(SQL_GET_ALL)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.format(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        final String SQL_REMOVE_ALL = "DELETE FROM Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format(e.getMessage());
        }
    }
}
