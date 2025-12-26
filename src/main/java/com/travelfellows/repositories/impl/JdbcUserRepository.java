package com.travelfellows.repositories.impl;

import com.travelfellows.models.User;
import com.travelfellows.repositories.AbstractJdbcRepository;

import java.sql.*;

public class JdbcUserRepository extends AbstractJdbcRepository {

    //language=SQL
    private final String SQL_INSERT = "insert into users (email, username, password_hash, salt) values (?, ?, ?, ?) returning id;";

    //language=SQL
    private final String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";

    //language=SQL
    private final String SQL_SELECT_BY_USERNAME = "select * from users where username = ?";

    //language=SQL
    private final String SQL_SELECT_BY_ID = "select * from users where id = ?";

    public JdbcUserRepository() {
        super();
    }

    public Long save(User user) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPasswordHash());
            preparedStatement.setString(4, user.getSalt());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            throw new SQLException("Не удалось создать пользователя");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return toUser(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USERNAME)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return toUser(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return toUser(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User toUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5)
        );
    }

}
