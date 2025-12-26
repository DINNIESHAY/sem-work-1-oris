package com.travelfellows.repositories.impl;

import com.travelfellows.models.Session;
import com.travelfellows.repositories.AbstractJdbcRepository;

import java.sql.*;

public class JdbcSessionRepository extends AbstractJdbcRepository {

    //language=SQL
    private final String SQL_INSERT = "insert into sessions (id, user_id, expire_at) values (?, ?, ?);";

    //language=SQL
    private final String SQL_SELECT_BY_ID = "select * from sessions where id = ?";

    //language=SQL
    private final String SQL_DELETE = "delete from sessions where id = ?";

    public JdbcSessionRepository() {
        super();
    }

    public void save(Session session) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, session.getSessionId());
            statement.setLong(2, session.getUserId());
            statement.setTimestamp(3, Timestamp.valueOf(session.getExpireAt()));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Session findById(String sessionId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setString(1, sessionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Session session = toSession(resultSet);
                resultSet.close();
                return session;
            }
            throw new IllegalArgumentException("Сессия не найдена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String sessionId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setString(1, sessionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Session toSession(ResultSet resultSet) throws SQLException {
        return new Session(
                resultSet.getString(1),
                resultSet.getLong(2),
                resultSet.getTimestamp(3).toLocalDateTime()
        );
    }
}
