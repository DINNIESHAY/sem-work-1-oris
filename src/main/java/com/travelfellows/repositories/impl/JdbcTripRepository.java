package com.travelfellows.repositories.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travelfellows.models.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelfellows.repositories.AbstractJdbcRepository;

public class JdbcTripRepository extends AbstractJdbcRepository {

    //language=SQL
    private final String SQL_INSERT_TRIP = """
        insert into trips (user_id, destination, departure_point, start_at, end_at, budget, max_fellows, description, status, tags)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::jsonb);
    """;

    //language=SQL
    private final String SQL_SELECT_TRIP_BY_ID = """
        select id, user_id, destination, departure_point, start_at, end_at, budget, max_fellows, description, status, tags
        from trips
        where id = ?;
    """;

    //language=SQL
    private final String SQL_SELECT_TRIPS_BY_USER_ID = """
        select id, user_id, destination, departure_point, start_at, end_at, budget, max_fellows, description, status, tags
        from trips
        where user_id = ?
        order by start_at desc;
    """;

    //language=SQL
    private final String SQL_SELECT_ALL_TRIPS = """
        select id, user_id, destination, departure_point, start_at, end_at, budget, max_fellows, description, status, tags
        from trips
        order by start_at desc;
    """;

    //language=SQL
    private final String SQL_UPDATE_TRIP = """
        update trips 
        set user_id = ?, destination = ?, departure_point = ?, start_at = ?, end_at = ?, budget = ?, max_fellows = ?, description = ?, status = ?, tags = ?::jsonb
        where id = ?;
    """;

    //language=SQL
    private final String SQL_DELETE_TRIP_BY_ID = """
        delete from trips where id = ?;
    """;

    //language=SQL
    private final String SQL_SELECT_TRIPS_BY_STATUS = """
        select id, user_id, destination, departure_point, start_at, end_at, budget, max_fellows, description, status, tags
        from trips
        where status = ?
        order by start_at desc;
    """;

    //language=SQL
    private final String SQL_SELECT_UPCOMING_TRIPS = """
        select id, user_id, destination, departure_point, start_at, end_at, budget, max_fellows, description, status, tags
        from trips
        where start_at > current_timestamp
        order by start_at asc;
    """;

    public JdbcTripRepository() {
        super();
    }

    public Trip save(Trip trip) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TRIP,
                     Statement.RETURN_GENERATED_KEYS)) {

            setTripParameters(statement, trip);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Не удалось создать трип.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return new Trip(generatedId, trip.getUserId(), trip.getDestination(),
                            trip.getDeparturePoint(), trip.getStartAt(), trip.getEndAt(),
                            trip.getBudget(), trip.getMaxFellows(), trip.getDescription(),
                            trip.getStatus(), trip.getTags());
                } else {
                    throw new SQLException("Не удалось создать трип.");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании трипа: " + e.getMessage());
        }
    }

    public Optional<Trip> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRIP_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toTrip(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти трип по id: " + id, e);
        }
    }

    public List<Trip> findByUserId(Long userId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRIPS_BY_USER_ID)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Trip> trips = new ArrayList<>();
                while (resultSet.next()) {
                    trips.add(toTrip(resultSet));
                }
                return trips;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка нахождения трипа " + userId + ": " + e.getMessage());
        }
    }

    public List<Trip> findAll() {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TRIPS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Trip> trips = new ArrayList<>();
            while (resultSet.next()) {
                trips.add(toTrip(resultSet));
            }
            return trips;

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти трипы: " + e.getMessage());
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TRIP_BY_ID)) {

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении трипа " + id + ": " + e.getMessage());
        }
    }

    public boolean update(Trip trip) {
        if (trip.getId() == null) {
            throw new IllegalArgumentException("ID трипа не найден.");
        }

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TRIP)) {

            int paramIndex = setTripParameters(statement, trip);
            statement.setLong(paramIndex, trip.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            throw new RuntimeException("Не удалось обновить трип. " + e.getMessage());
        }
    }

    private int setTripParameters(PreparedStatement statement, Trip trip) throws SQLException, JsonProcessingException {
        int index = 1;

        statement.setLong(index++, trip.getUserId());
        statement.setString(index++, trip.getDestination());
        statement.setString(index++, trip.getDeparturePoint());

        if (trip.getStartAt() != null) {
            statement.setTimestamp(index++, Timestamp.valueOf(trip.getStartAt().toString()));
        } else {
            statement.setNull(index++, Types.TIMESTAMP);
        }

        if (trip.getEndAt() != null) {
            statement.setTimestamp(index++, Timestamp.valueOf(trip.getEndAt().toString()));
        } else {
            statement.setNull(index++, Types.TIMESTAMP);
        }

        if (trip.getBudget() != null) {
            statement.setBigDecimal(index++, trip.getBudget());
        } else {
            statement.setNull(index++, Types.NUMERIC);
        }

        if (trip.getMaxFellows() != null) {
            statement.setInt(index++, trip.getMaxFellows());
        } else {
            statement.setNull(index++, Types.INTEGER);
        }

        statement.setString(index++, trip.getDescription());
        statement.setString(index++, trip.getStatus());

        if (trip.getTags() != null && !trip.getTags().isEmpty()) {
            String tagsJson = new ObjectMapper().writeValueAsString(trip.getTags());
            statement.setObject(index++, tagsJson, Types.OTHER);
        } else {
            statement.setNull(index++, Types.OTHER);
        }

        return index;
    }

    private Trip toTrip(ResultSet resultSet) throws SQLException {
        List<String> tags = null;
        try {
            String tagsJson = resultSet.getString("tags");
            if (tagsJson != null) {
                tags = new com.fasterxml.jackson.databind.ObjectMapper()
                        .readValue(tagsJson, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {});
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении тэгов: " + e.getMessage());
        }

        return new Trip(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getString("destination"),
                resultSet.getString("departure_point"),
                resultSet.getTimestamp("start_at") != null ?
                        new Date(resultSet.getTimestamp("start_at").getTime()) : null,
                resultSet.getTimestamp("end_at") != null ?
                        new Date(resultSet.getTimestamp("end_at").getTime()) : null,
                resultSet.getBigDecimal("budget"),
                resultSet.getInt("max_fellows"),
                resultSet.getString("description"),
                resultSet.getString("status"),
                tags
        );
    }
}
