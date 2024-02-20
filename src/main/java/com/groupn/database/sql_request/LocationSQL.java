package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LocationSQL {
    default Location getLocationById(int locationId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getLocationById.");
            String sql = "SELECT * FROM Location WHERE location_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, locationId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    return manager.getMapper().mapResultSetToLocation(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }
    default List<Location> getAllLocation(DBManager manager){
        List<Location> locations = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getAllEvents.");
            String sql = "SELECT * FROM Location";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Location location = manager.getMapper().mapResultSetToLocation(resultSet);
                    locations.add(location);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return locations;
    }
}
