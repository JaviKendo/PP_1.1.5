package jm.task.core.jdbc.dao.java.jm.task.core.jdbc.dao;

import jm.task.core.jdbc.dao.java.jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.java.jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "create table if not exists users\n" +
                "(\n" +
                "    id       bigint auto_increment\n" +
                "        primary key,\n" +
                "    name     varchar(50),\n" +
                "    lastName varchar(50),\n" +
                "    age      tinyint(3)\n" +
                ")";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String query = "insert into users (name, lastName, age) " +
                "values (?, ?, ?)";

        Connection connection = Util.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        String query = "delete from users where id = ?";

        Connection connection = Util.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String query = "select * from users";

        List<User> usersList = new ArrayList<>();

        Connection connection = Util.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));

                usersList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return usersList;
    }

    public void cleanUsersTable() {
        String query = "truncate table users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}