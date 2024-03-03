package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.EventObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EventObjectsSQL {
    default List<EventObjects> getAllEventObjects(DBManager manager) {
        List<EventObjects> eventObjectsList = new ArrayList<>();

        try {
            manager.getLogger().info("RUN getAllEventObjects.");

            String sql = "SELECT * FROM EventObjects";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int eventId = resultSet.getInt("event_id");
                    int artObjectId = resultSet.getInt("art_object_id");
                    eventObjectsList.add(new EventObjects(eventId,artObjectId));
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return eventObjectsList;
    }

    default void deleteEventObject(int eventId, int artObjectId, DBManager manager) {
        try {
            manager.getLogger().info("RUN deleteEventObject.");

            String sql = "DELETE FROM EventObjects WHERE event_id = ? AND art_object_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, eventId);
                preparedStatement.setInt(2, artObjectId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }
}