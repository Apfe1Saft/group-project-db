package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.ArtObject;
import com.groupn.entities.Event;
import com.groupn.entities.Owner;
import com.groupn.entities.Purchase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    default List<Owner> getOwnersByFilter(String filter, DBManager manager) {
        List<Owner> owners = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getOwnersByFilter.");

            String sql = "SELECT * FROM Owner WHERE owner_name LIKE ? OR owner_description LIKE ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + filter + "%");
                preparedStatement.setString(2, "%" + filter + "%");

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

    default void updateOwner(Owner owner, DBManager manager) {
        try {
            manager.getLogger().info("RUN updateOwner.");

            String sql = "UPDATE Owner " +
                    "SET owner_name = ?, owner_description = ? " +
                    "WHERE owner_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getDescription());
                preparedStatement.setInt(3, owner.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void addOwner(Owner owner, DBManager manager) {
        try {
            manager.getLogger().info("RUN addOwner.");

            String sql = "INSERT INTO Owner (owner_name, owner_description) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getDescription());
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void removeOwner(int ownerId, DBManager manager) {
        try {
            //! update Purchase, update ArtObject
            manager.getLogger().info("RUN removeOwner.");

            String sql = "DELETE FROM Owner WHERE owner_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, ownerId);

                for (Purchase purchase : manager.getAllPurchases().stream().filter(x -> x.getSeller().getId() == ownerId).collect(Collectors.toList())) {
                    purchase.setSeller(null);
                    manager.updatePurchase(purchase);
                }

                for (Purchase purchase : manager.getAllPurchases().stream().filter(x -> x.getBuyer().getId() == ownerId).collect(Collectors.toList())) {
                    purchase.setBuyer(null);
                    manager.updatePurchase(purchase);
                }

                for (ArtObject artObject : manager.getAllArtObjects().stream().filter(x -> x.getCurrentOwner().getId() == ownerId).collect(Collectors.toList())) {
                    artObject.setCurrentOwner(null);
                    manager.updateArtObject(artObject);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

}
