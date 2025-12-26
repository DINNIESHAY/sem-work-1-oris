package com.travelfellows.repositories.impl;

import com.travelfellows.repositories.AbstractJdbcRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcFavoritesRepository extends AbstractJdbcRepository {

    //language=SQL
    private final String SQL_INSERT_FAVORITE = """
        insert into favorites (user_id, trip_id) 
        values (?, ?)
    """;

    //language=SQL
    private final String SQL_DELETE_FAVORITE = """
        delete from favorites 
        where user_id = ? and trip_id = ?
    """;

    //language=SQL
    private final String SQL_SELECT_FAVORITES_BY_USER = """
        select trip_id from favorites 
        where user_id = ?
    """;

    //language=SQL
    private final String SQL_CHECK_IS_FAVORITE = """
        select 1 from favorites 
        where user_id = ? and trip_id = ?
    """;

    //language=SQL
    private final String SQL_COUNT_LIKES = """
        select count(*) as likes_count
        from favorites
        where trip_id = ?;
    """;

    public JdbcFavoritesRepository() {
        super();
    }

    public boolean save(Long userId, Long tripId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FAVORITE)) {

            statement.setLong(1, userId);
            statement.setLong(2, tripId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении в избранное: " + e.getMessage(), e);
        }
    }

    public boolean delete(Long userId, Long tripId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FAVORITE)) {

            statement.setLong(1, userId);
            statement.setLong(2, tripId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении из избранного: " + e.getMessage(), e);
        }
    }

    public List<Long> findFavoriteTripIds(Long userId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FAVORITES_BY_USER)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> tripIds = new ArrayList<>();
                while (resultSet.next()) {
                    tripIds.add(resultSet.getLong("trip_id"));
                }
                return tripIds;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении избранных поездок: " + e.getMessage(), e);
        }
    }

    public int countLikes(Long tripId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_LIKES)) {

            preparedStatement.setLong(1, tripId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("likes_count");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подсчете лайков: " + e.getMessage());
        }
    }
}