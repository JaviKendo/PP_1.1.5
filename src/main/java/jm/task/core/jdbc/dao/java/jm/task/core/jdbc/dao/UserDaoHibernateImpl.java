////package jm.task.core.jdbc.dao;
////
////import jm.task.core.jdbc.model.User;
////import jm.task.core.jdbc.util.Util;
////import org.hibernate.Session;
////import org.hibernate.SessionFactory;
////
////import java.util.List;
//
//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.*;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//
//import java.util.List;
//
//public class UserDaoHibernateImpl implements UserDao {
//    private SessionFactory sessionFactory = Util.getSessionFactory();
//    public UserDaoHibernateImpl() {
//
//    }
//
//
//    @Override
//    public void createUsersTable() {
//        String query = "create table if not exists users" +
//                "(" +
//                "    id       bigint auto_increment" +
//                "        primary key," +
//                "    name     varchar(50)," +
//                "    lastName varchar(50)," +
//                "    age      tinyint(3)" +
//                ")";
//
//        try (Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            session.createSQLQuery(query).executeUpdate();
//            session.getTransaction().commit();
//        }
//    }
//
//    @Override
//    public void dropUsersTable() {
//        String query = "drop table if exists users";
//
//        try (Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            session.createSQLQuery(query).executeUpdate();
//            session.getTransaction().commit();
//        }
//    }
//
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
////        SessionFactory sessionFactory = Util.getSessionFactory();
////        Session session = sessionFactory.getCurrentSession();
//
//        try /*(SessionFactory sessionFactory = Util.getSessionFactory();*/
//                (Session session = sessionFactory.getCurrentSession()) {
//            User user = new User(name, lastName, age);
//            session.beginTransaction();
////            session.save(user);
//            session.persist(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
////            session.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
////            session.close();
//        }
//    }
//
//    @Override
//    public void removeUserById(long id) {
////        SessionFactory sessionFactory = Util.getSessionFactory();
////        Session session = sessionFactory.getCurrentSession();
//
//        try(Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            User user = session.get(User.class, id);
////                session.delete(user);
//            session.remove(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
////            session.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
////            session.close();
//        }
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        List<User> usersList = null;
//
////        SessionFactory sessionFactory = Util.getSessionFactory();
////        Session session = sessionFactory.getCurrentSession();
//
//        try (Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            usersList = session.createQuery("from User", User.class).getResultList();
//            session.getTransaction().commit();
//        } catch (Exception e) {
////            session.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
////            session.close();
//        }
//
//        return usersList;
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        String query = "truncate table users";
//
//        try (Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            session.createSQLQuery(query).executeUpdate();
//            session.getTransaction().commit();
//        }
//    }
//}






package jm.task.core.jdbc.dao.java.jm.task.core.jdbc.dao;

import jm.task.core.jdbc.dao.java.jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.management.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory  = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String sql = "create table if not exists user\n" +
                    "                    (\n" +
                    "                            ID       bigint auto_increment\n" +
                    "                            primary key,\n" +
                    "                            NAME     varchar(50) not null,\n" +
                    "                    LASTNAME varchar(50) not null,\n" +
                    "                    AGE      tinyint (3)   not null\n" +
                    ");";

            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("drop table if exists user").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.save(new jm.task.core.jdbc.dao.java.jm.task.core.jdbc.model.User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User where id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public List<jm.task.core.jdbc.dao.java.jm.task.core.jdbc.model.User> getAllUsers() {
        List<jm.task.core.jdbc.dao.java.jm.task.core.jdbc.model.User> users ;
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            users = session.createQuery("from User order by name").list();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
