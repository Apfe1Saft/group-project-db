package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface EventSQL {
    default Event getEventById(int eventId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getEventById.");
            String sql = "SELECT * FROM Event WHERE event_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, eventId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    return manager.getMapper().mapResultSetToEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }
}
