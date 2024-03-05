package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.Purchase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseSQL {
    default void addPurchase(Purchase purchase, DBManager manager) {
        try {
            manager.getLogger().info(" RUN addPurchase.");
            String sql = "INSERT INTO Purchase (purchase_date, purchase_price, art_object_id, seller_id, buyer_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(purchase.getDateOfPurchase()));
                preparedStatement.setInt(2, purchase.getPrice());
                preparedStatement.setInt(3, purchase.getArtObject().getId());
                preparedStatement.setInt(4, purchase.getSeller().getId());
                preparedStatement.setInt(5, purchase.getBuyer().getId());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    purchase.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default Purchase getPurchaseId(int purchaseId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getPurchaseById.");
            String sql = "SELECT * FROM Purchase WHERE purchase_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, purchaseId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return manager.getMapper().mapResultSetToPurchase(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }

    default List<Purchase> getAllPurchases(DBManager manager){
        List<Purchase> purchases = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getAllEvents.");
            String sql = "SELECT * FROM Purchase";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Purchase purchase = manager.getMapper().mapResultSetToPurchase(resultSet);
                    purchases.add(purchase);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return purchases;
    }

    default List<Purchase> getPurchasesByFilter(String filter, DBManager manager) {
        List<Purchase> purchases = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getPurchasesByFilter.");

            String sql = "SELECT * FROM Purchase WHERE CAST(purchase_date AS VARCHAR) LIKE ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + filter + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Purchase purchase = manager.getMapper().mapResultSetToPurchase(resultSet);
                    purchases.add(purchase);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return purchases;
    }

    default void updatePurchase(Purchase purchase, DBManager manager) {
        try {
            manager.getLogger().info("RUN updatePurchase.");

            String sql = "UPDATE Purchase " +
                    "SET purchase_date = ?, purchase_price = ?, art_object_id = ?, seller_id = ?, buyer_id = ? " +
                    "WHERE purchase_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(purchase.getDateOfPurchase()));
                preparedStatement.setInt(2, purchase.getPrice());
                preparedStatement.setInt(3, purchase.getArtObject().getId());
                preparedStatement.setInt(4, purchase.getSeller().getId());
                preparedStatement.setInt(5, purchase.getBuyer().getId());
                preparedStatement.setInt(6, purchase.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void removePurchase(int purchaseId, DBManager manager) {
        try {
            manager.getLogger().info("RUN removePurchase.");

            String sql = "DELETE FROM Purchase WHERE purchase_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, purchaseId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }
}
