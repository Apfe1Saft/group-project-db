package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.Owner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OwnerSQL {
    default Owner getOwnerById(int ownerId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getLocationTypeById.");
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

    default List<Owner> getAllOwners(DBManager manager){
        List<Owner> owners = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getAllOwners.");
            String sql = "SELECT * FROM Owner";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Owner owner = manager.getMapper().mapResultSetToOwner(resultSet);
                    owners.add(owner);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return owners;
    }
}
