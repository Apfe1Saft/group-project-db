package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.ArtObject;
import com.groupn.entities.Event;
import com.groupn.entities.Location;
import com.groupn.entities.LocationType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    default List<Location> getLocationsByFilter(String filter, DBManager manager) {
        List<Location> locations = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getLocationsByFilter.");

            String sql = "SELECT * FROM Location WHERE location_name LIKE ? OR location_description LIKE ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + filter + "%");
                preparedStatement.setString(2, "%" + filter + "%");

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

    default void addLocation(Location location, DBManager manager) {
        try {
            manager.getLogger().info("RUN addLocation.");
            int locationType = 1;
            if (location.getType() == LocationType.PRIVATE_LOCATION)
                locationType = 2;

            String sql = "INSERT INTO Location (location_name, location_description, placement, date_of_opening, location_type_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, location.getName());
                preparedStatement.setString(2, location.getDescription());
                preparedStatement.setString(3, location.getPlacement());
                preparedStatement.setDate(4, java.sql.Date.valueOf(location.getDateOfOpening()));
                preparedStatement.setInt(5, locationType);
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void updateLocation(Location location, DBManager manager) {
        try {
            manager.getLogger().info("RUN updateLocation.");
            int locationType = 1;
            if (location.getType() == LocationType.PRIVATE_LOCATION)
                locationType = 2;
            String sql = "UPDATE Location " +
                    "SET location_name = ?, location_description = ?, placement = ?, date_of_opening = ?, location_type_id = ? " +
                    "WHERE location_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, location.getName());
                preparedStatement.setString(2, location.getDescription());
                preparedStatement.setString(3, location.getPlacement());
                preparedStatement.setDate(4, java.sql.Date.valueOf(location.getDateOfOpening()));
                preparedStatement.setInt(5, locationType);
                preparedStatement.setInt(6, location.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void removeLocation(int locationId, DBManager manager) {
        try {
            manager.getLogger().info("RUN removeAuthor.");

            String checkDependenciesSql = "SELECT 1 FROM ArtObject WHERE current_location_id = ?";
            try (PreparedStatement checkDependenciesStatement = manager.getConnection().prepareStatement(checkDependenciesSql)) {
                checkDependenciesStatement.setInt(1, locationId);
                try (ResultSet resultSet = checkDependenciesStatement.executeQuery()) {
                    if (resultSet.next()) {
                        manager.getLogger().info("Handling dependencies in ArtObject table.");
                        String handleDependenciesSql = "UPDATE ArtObject SET current_location_id = NULL WHERE current_location_id = ?";
                        try (PreparedStatement handleDependenciesStatement = manager.getConnection().prepareStatement(handleDependenciesSql)) {
                            handleDependenciesStatement.setInt(1, locationId);
                            handleDependenciesStatement.executeUpdate();
                        }
                    }
                }
            }

            String deleteAuthorSql = "DELETE FROM Location WHERE location_id = ?";
            try (PreparedStatement deleteAuthorStatement = manager.getConnection().prepareStatement(deleteAuthorSql)) {
                deleteAuthorStatement.setInt(1, locationId);
                deleteAuthorStatement.executeUpdate();
                manager.getLogger().info("Location removed successfully.");
            }

        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }
}
