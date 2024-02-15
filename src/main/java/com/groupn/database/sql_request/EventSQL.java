package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.EventType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface EventSQL {
    default EventType getEventTypeById(int eventTypeId, DBManager manager) {
        try {
            String sql = "SELECT * FROM EventType WHERE event_type_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, eventTypeId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return switch (resultSet.getInt("event_type_id")) {
                        case 1 -> EventType.EXHEBITION;
                        case 2 -> EventType.WRONGDOOR;
                        default -> throw new Exception("Wrong Event Type");
                    };
                }
            }
        } catch (Exception e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }
}
