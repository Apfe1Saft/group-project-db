package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.LocationType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface LocationTypeSQL {
    default LocationType getLocationTypeById(int locationTypeId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getLocationTypeById.");
            String sql = "SELECT * FROM LocationType WHERE location_type_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, locationTypeId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    return switch (resultSet.getInt("location_type_id")) {
                        case 1 -> LocationType.MUSEUM;
                        case 2 -> LocationType.PRIVATE_LOCATION;
                        default -> throw new Exception("Wrong Location Type");
                    };
                }
            }
        } catch (Exception e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }
}
