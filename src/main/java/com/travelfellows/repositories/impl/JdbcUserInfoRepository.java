package com.travelfellows.repositories.impl;

import com.travelfellows.models.UserInfo;
import com.travelfellows.repositories.AbstractJdbcRepository;

import java.sql.*;
import java.util.Optional;

public class JdbcUserInfoRepository extends AbstractJdbcRepository {

    //language=SQL
    private final String SQL_INSERT_USER_INFO = """
            INSERT INTO users_info (user_id, first_name, last_name, email, phone_number, date_of_birth, country, city, avatar_url, bio)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    //language=SQL
    private final String SQL_SELECT_USER_BY_ID = """
            SELECT user_id, first_name, last_name, email, phone_number, date_of_birth, country, city, avatar_url, bio
            FROM users_info
            WHERE user_id = ?
        """;

    //language=SQL
    private final String SQL_UPDATE_USER_INFO = """
            UPDATE users_info 
            SET first_name = ?, last_name = ?, email = ?, phone_number = ?, date_of_birth = ?, country = ?, city = ?, avatar_url = ?, bio = ?
            WHERE user_id = ?
        """;

    //language=SQL
    private final String SQL_DELETE_USER_BY_ID = """
            DELETE FROM users_info WHERE user_id = ?
        """;

    public JdbcUserInfoRepository() {
        super();
    }

    public boolean save(UserInfo userInfo) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_INFO)) {

            statement.setLong(1, userInfo.getUserId());
            setUserInfoParameters(statement, userInfo, 2);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пользователя: " + e.getMessage());
        }
    }

    public Optional<UserInfo> findById(Long userId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toUserInfo(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователя: " + userId + ": " + e.getMessage());
        }
    }

    public boolean update(UserInfo userInfo) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_INFO)) {

            int paramIndex = setUserInfoParameters(statement, userInfo, 1);
            statement.setLong(paramIndex, userInfo.getUserId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя:" + e.getMessage());
        }
    }

    public boolean delete(Long userId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {

            statement.setLong(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя с данным id: " + userId, e);
        }
    }

    public boolean existsById(Long userId) {
        return findById(userId).isPresent();
    }

    private int setUserInfoParameters(PreparedStatement statement, UserInfo userInfo, int startIndex)
            throws SQLException {
        int index = startIndex;

        statement.setString(index++, userInfo.getFirstName());
        statement.setString(index++, userInfo.getLastName());
        statement.setString(index++, userInfo.getEmail());
        statement.setString(index++, userInfo.getPhoneNumber());

        if (userInfo.getDateOfBirth() != null) {
            statement.setDate(index++, Date.valueOf(userInfo.getDateOfBirth()));
        } else {
            statement.setNull(index++, Types.DATE);
        }

        statement.setString(index++, userInfo.getCountry());
        statement.setString(index++, userInfo.getCity());
        statement.setString(index++, userInfo.getAvatarUrl());
        statement.setString(index++, userInfo.getBio());

        return index;
    }

    private UserInfo toUserInfo(ResultSet resultSet) throws SQLException {
        return new UserInfo(
                resultSet.getLong("user_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone_number"),
                resultSet.getDate("date_of_birth") != null ?
                        resultSet.getDate("date_of_birth").toLocalDate() : null,
                resultSet.getString("country"),
                resultSet.getString("city"),
                resultSet.getString("avatar_url"),
                resultSet.getString("bio")
        );
    }
}
