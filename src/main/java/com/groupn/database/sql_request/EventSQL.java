package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.ArtObject;
import com.groupn.entities.Event;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EventSQL {
    String eventJOINQuery = "SELECT * " +
            "FROM Event as ev " +
            "JOIN Location as lo ON ev.event_location_id=lo.location_id ";

    default Event getEventById(int eventId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getEventById.");
            String sql = eventJOINQuery + " WHERE event_id = ?";
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

    default List<Event> getAllEvents(DBManager manager) {
        List<Event> events = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getAllEvents.");
            String sql = eventJOINQuery;
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Event event = manager.getMapper().mapResultSetToEvent(resultSet);
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return events;
    }

    default void addEvent(Event event, DBManager manager) {
        try {
            manager.getLogger().info(" RUN addEvent.");
            String sql = "INSERT INTO Event (event_name, event_type_id, event_description, event_start_date, event_end_date, event_location_id, event_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, event.getName());
                preparedStatement.setInt(2, event.getType().ordinal()+1);
                preparedStatement.setString(3, event.getDescription());
                preparedStatement.setDate(4, Date.valueOf(event.getStartDateOfEvent()));
                preparedStatement.setDate(5, Date.valueOf(event.getEndDateOfEvent()));
                preparedStatement.setInt(6, event.getLocation().getId());
                preparedStatement.setInt(7, event.getPrice());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    event.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default List<Event> getEventsByFilter(String filter, DBManager manager) {
        List<Event> events = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getEventsByFilter.");

            String sql = eventJOINQuery + " WHERE ev.event_description LIKE ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + filter + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Event event = manager.getMapper().mapResultSetToEvent(resultSet);
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return events;
    }
}
