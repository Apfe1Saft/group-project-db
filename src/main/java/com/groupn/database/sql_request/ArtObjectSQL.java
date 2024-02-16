package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.ArtObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ArtObjectSQL {

    default ArtObject getArtObjectById(int artObjectId, DBManager manager) {
        try {
            manager.getLogger().info("RUN getArtObjectById.");
            String sql = "SELECT * FROM ArtObject WHERE art_object_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, artObjectId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    return manager.getMapper().mapResultSetToArtObject(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }

    default List<ArtObject> getArtObjectByAuthor(int authorId, DBManager manager) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getArtObjectByAuthor.");
            String sql = "SELECT * FROM ArtObject WHERE author_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, authorId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    ArtObject artObject = manager.getMapper().mapResultSetToArtObject(resultSet);
                    artObjects.add(artObject);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return artObjects;
    }

    default List<ArtObject> getArtObjectByOwner(int ownerId, DBManager manager) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getArtObjectByOwner.");
            String sql = "SELECT * FROM ArtObject WHERE current_owner_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, ownerId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    ArtObject artObject = manager.getMapper().mapResultSetToArtObject(resultSet);
                    artObjects.add(artObject);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return artObjects;
    }

    default List<ArtObject> getArtObjectByLocation(int locationId, DBManager manager) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getArtObjectByOwner.");
            String sql = "SELECT * FROM ArtObject WHERE current_location_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, locationId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    ArtObject artObject = manager.getMapper().mapResultSetToArtObject(resultSet);
                    artObjects.add(artObject);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return artObjects;
    }
}
