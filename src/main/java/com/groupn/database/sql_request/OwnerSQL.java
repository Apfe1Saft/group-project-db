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
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void removeOwner(int ownerId, DBManager manager) {
        try {
            manager.getLogger().info("RUN removeAuthor.");

            String checkDependenciesSql = "SELECT 1 FROM ArtObject WHERE current_owner_id = ?";
            try (PreparedStatement checkDependenciesStatement = manager.getConnection().prepareStatement(checkDependenciesSql)) {
                checkDependenciesStatement.setInt(1, ownerId);
                try (ResultSet resultSet = checkDependenciesStatement.executeQuery()) {
                    if (resultSet.next()) {
                        manager.getLogger().info("Handling dependencies in ArtObject table.");
                        String handleDependenciesSql = "UPDATE ArtObject SET current_owner_id = NULL WHERE current_owner_id = ?";
                        try (PreparedStatement handleDependenciesStatement = manager.getConnection().prepareStatement(handleDependenciesSql)) {
                            handleDependenciesStatement.setInt(1, ownerId);
                            handleDependenciesStatement.executeUpdate();
                        }
                    }
                }
            }

            checkDependenciesSql = "SELECT 1 FROM Purchase WHERE seller_id = ?";
            try (PreparedStatement checkDependenciesStatement = manager.getConnection().prepareStatement(checkDependenciesSql)) {
                checkDependenciesStatement.setInt(1, ownerId);
                try (ResultSet resultSet = checkDependenciesStatement.executeQuery()) {
                    if (resultSet.next()) {
                        manager.getLogger().info("Handling dependencies in Purchase table.");
                        String handleDependenciesSql = "UPDATE Purchase SET seller_id = NULL WHERE seller_id = ?";
                        try (PreparedStatement handleDependenciesStatement = manager.getConnection().prepareStatement(handleDependenciesSql)) {
                            handleDependenciesStatement.setInt(1, ownerId);
                            handleDependenciesStatement.executeUpdate();
                        }
                    }
                }
            }

            checkDependenciesSql = "SELECT 1 FROM Purchase WHERE buyer_id = ?";
            try (PreparedStatement checkDependenciesStatement = manager.getConnection().prepareStatement(checkDependenciesSql)) {
                checkDependenciesStatement.setInt(1, ownerId);
                try (ResultSet resultSet = checkDependenciesStatement.executeQuery()) {
                    if (resultSet.next()) {
                        manager.getLogger().info("Handling dependencies in Purchase table.");
                        String handleDependenciesSql = "UPDATE Purchase SET buyer_id = NULL WHERE buyer_id = ?";
                        try (PreparedStatement handleDependenciesStatement = manager.getConnection().prepareStatement(handleDependenciesSql)) {
                            handleDependenciesStatement.setInt(1, ownerId);
                            handleDependenciesStatement.executeUpdate();
                        }
                    }
                }
            }

            String deleteAuthorSql = "DELETE FROM Owner WHERE owner_id = ?";
            try (PreparedStatement deleteAuthorStatement = manager.getConnection().prepareStatement(deleteAuthorSql)) {
                deleteAuthorStatement.setInt(1, ownerId);
                deleteAuthorStatement.executeUpdate();
                manager.getLogger().info("Owner removed successfully.");
            }

        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

}
