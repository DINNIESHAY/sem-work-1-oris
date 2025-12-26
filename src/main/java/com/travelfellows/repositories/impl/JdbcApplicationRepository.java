package com.travelfellows.repositories.impl;

import com.travelfellows.models.TripApplication;
import com.travelfellows.repositories.AbstractJdbcRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcApplicationRepository extends AbstractJdbcRepository {

    //language=SQL
    private final String SQL_INSERT_APPLICATION = """
        insert into trip_applications (user_id, trip_id, message, contacts) 
        values (?, ?, ?, ?)
    """;

    //language=SQL
    private final String SQL_UPDATE_APPLICATION = """
        update trip_applications 
        set message = ?, contacts = ?
        where id = ?
    """;

    //language=SQL
    private final String SQL_DELETE_APPLICATION = """
        delete from trip_applications 
        where id = ?
    """;

    //language=SQL
    private final String SQL_SELECT_APPLICATION_BY_ID = """
        select * from trip_applications 
        where id = ?
    """;

    //language=SQL
    private final String SQL_SELECT_APPLICATIONS_BY_USER = """
        select * from trip_applications 
        where user_id = ?
    """;

    //language=SQL
    private final String SQL_SELECT_APPLICATIONS_BY_TRIP = """
        select * from trip_applications 
        where trip_id = ?
    """;

    //language=SQL
    private final String SQL_SELECT_APPLICATION_BY_USER_AND_TRIP = """
        select * from trip_applications 
        where user_id = ? and trip_id = ?
    """;

    //language=SQL
    private final String SQL_COUNT_APPLICATIONS_BY_TRIP = """
        select count(*) as applications_count
        from trip_applications
        where trip_id = ?
    """;

    //language=SQL
    private final String SQL_CHECK_APPLICATION_EXISTS = """
        select 1 from trip_applications 
        where user_id = ? and trip_id = ?
    """;

    public JdbcApplicationRepository() {
        super();
    }

    public void save(TripApplication application) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_APPLICATION, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, application.getUserId());
            statement.setLong(2, application.getTripId());
            statement.setString(3, application.getMessage());
            statement.setString(4, application.getContacts());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Создание заявки не удалось, ни одна запись не была изменена.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении заявки: " + e.getMessage(), e);
        }
    }

    public List<TripApplication> findByTripId(Long tripId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_APPLICATIONS_BY_TRIP)) {

            statement.setLong(1, tripId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<TripApplication> applications = new ArrayList<>();
                while (resultSet.next()) {
                    applications.add(toApplication(resultSet));
                }
                return applications;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске заявок на поездку: " + e.getMessage(), e);
        }
    }

    public boolean existsByUserAndTrip(Long userId, Long tripId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_APPLICATION_EXISTS)) {

            statement.setLong(1, userId);
            statement.setLong(2, tripId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке существования заявки: " + e.getMessage(), e);
        }
    }

    private TripApplication toApplication(ResultSet resultSet) throws SQLException {

        return new TripApplication(resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("trip_id"),
                resultSet.getString("message"),
                resultSet.getString("contacts"));
    }
}