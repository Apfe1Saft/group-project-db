package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.Owner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface OwnerSQL {
    default Owner getOwnerById(int ownerId, DBManager manager) {
        try {
            String sql = "SELECT * FROM Owner WHERE owner_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, ownerId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return manager.getMapper().mapResultSetToOwner(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }
}
