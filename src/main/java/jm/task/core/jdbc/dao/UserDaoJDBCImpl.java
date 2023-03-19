package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

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
                "    age      tinyint\n" +
                ");";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists users;";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into users (name, lastName, age) " +
                "values (?, ?, ?);";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users;";

        List<User> usersList = new ArrayList<>();

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));

                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public void cleanUsersTable() {
        String query = "truncate table users;";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}