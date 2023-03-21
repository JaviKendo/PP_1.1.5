package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();

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
        Session session = sessionFactory.getCurrentSession();

        try {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = null;

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            usersList = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        String query = "truncate table users";

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        }
    }
}