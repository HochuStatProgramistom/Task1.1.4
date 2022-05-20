package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final String SQL_CREATE_TABLE = "CREATE TABLE Users"
                    + "("
                    + " ID INT NOT NULL AUTO_INCREMENT,"
                    + " NAME varchar(100) NOT NULL,"
                    + " LASTNAME VARCHAR(100) NOT NULL,"
                    + " AGE INT NOT NULL,"
                    + " PRIMARY KEY (ID)"
                    + ")";
            Query query = session.createSQLQuery(SQL_CREATE_TABLE);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (!e.getMessage().equals("Table 'users' already exists")) {
                System.err.format(e.getMessage());
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final String SQL_DROP_TABLE = "DROP TABLE Users";
            Query query = session.createSQLQuery(SQL_DROP_TABLE);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (!e.getMessage().equals("Unknown table 'task_1.users'")) {
                System.err.format(e.getMessage());
            }
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception tException) {
                if (!tException.getMessage().equals("Unknown table 'task_1.users'")) {
                    System.err.format(e.getMessage());
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> usersList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            usersList = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
