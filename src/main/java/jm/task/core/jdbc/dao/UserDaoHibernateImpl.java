package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String query = "create table if not exists users" +
                "(" +
                "    id       bigint auto_increment" +
                "        primary key," +
                "    name     varchar(50)," +
                "    lastName varchar(50)," +
                "    age      tinyint(3)" +
                ")";

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "drop table if exists users";

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = Collections.emptyList();

        Session session = Util.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            usersList = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}