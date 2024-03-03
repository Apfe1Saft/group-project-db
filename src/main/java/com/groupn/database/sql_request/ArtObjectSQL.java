package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.ArtObject;
import com.groupn.entities.EventObjects;
import com.groupn.entities.Purchase;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ArtObjectSQL {
    String artObjectJOINQuery = "SELECT * " +
            "FROM ArtObject as ao " +
            "JOIN Author as au ON ao.author_id = au.author_id " +
            "JOIN Owner as ow ON ao.current_owner_id = ow.owner_id " +
            "JOIN Location as lo ON ao.current_location_id = lo.location_id ";

    default ArtObject getArtObjectById(int artObjectId, DBManager manager) {
        try {
            manager.getLogger().info("RUN getArtObjectById.");
            String sql = artObjectJOINQuery + "WHERE ao.art_object_id = ?";
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
            String sql = artObjectJOINQuery + " WHERE ao.author_id = ?";
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
            String sql = artObjectJOINQuery + " WHERE ao.current_owner_id = ?";
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
            String sql = artObjectJOINQuery + " WHERE ao.current_location_id = ?";
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

    default List<ArtObject> getAllArtObjects(DBManager manager) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getAllArtObjects.");
            String sql = artObjectJOINQuery;
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
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

    default void addArtObject(ArtObject artObject, DBManager manager) {
        try {
            manager.getLogger().info(" RUN addArtObject.");
            String sql = "INSERT INTO ArtObject (art_object_name, art_object_description, author_id, current_owner_id, current_location_id,date_of_creation) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, artObject.getName());
                preparedStatement.setString(2, artObject.getDescription());
                preparedStatement.setInt(3, artObject.getAuthor().getId());
                preparedStatement.setInt(4, artObject.getCurrentOwner().getId());
                preparedStatement.setInt(5, artObject.getCurrentLocation().getId());
                preparedStatement.setDate(6, Date.valueOf(artObject.getDateOfCreation()));
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    artObject.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void removeArtObjectById(int artObjectId, DBManager manager) {
        try {
            //! update Purchase, update Event
            manager.getLogger().info("RUN removeArtObjectById.");
            String sql = "DELETE FROM ArtObject WHERE art_object_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, artObjectId);
                preparedStatement.executeUpdate();

                for (Purchase purchase : manager.getAllPurchases().stream().filter(x -> x.getArtObject().getId() == artObjectId).collect(Collectors.toList())) {
                    purchase.setArtObject(null);
                    manager.updatePurchase(purchase);
                }

                List<EventObjects> eventObjectsList = manager.getAllEventObjects().stream().filter(x->x.getArtObjectId() == artObjectId).collect(Collectors.toList());
                for(EventObjects eventObjects: eventObjectsList){
                    manager.deleteEventObject(eventObjects.getEventId(),eventObjects.getArtObjectId());
                }

            }


        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void updateArtObject(ArtObject artObject, DBManager manager) {
        try {
            manager.getLogger().info("RUN updateArtObject.");
            String sql = "UPDATE ArtObject SET art_object_name = ?, art_object_description = ?, " +
                    "author_id = ?, current_owner_id = ?, current_location_id = ?, " +
                    "date_of_creation = ? WHERE art_object_id = ?";

            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, artObject.getName());
                preparedStatement.setString(2, artObject.getDescription());
                preparedStatement.setInt(3, artObject.getAuthor().getId());
                preparedStatement.setInt(4, artObject.getCurrentOwner().getId());
                preparedStatement.setInt(5, artObject.getCurrentLocation().getId());
                preparedStatement.setDate(6, Date.valueOf(artObject.getDateOfCreation()));
                preparedStatement.setInt(7, artObject.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default List<ArtObject> getArtObjectsByFilter(String filter, DBManager manager) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getArtObjectsByFilter.");

            String sql = artObjectJOINQuery + " WHERE ao.art_object_name LIKE ? OR ao.art_object_description LIKE ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + filter + "%");
                preparedStatement.setString(2, "%" + filter + "%");

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

    default List<ArtObject> getArtObjectsByEventId(int eventId, DBManager manager) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getArtObjectsByEventId.");

            String sql = artObjectJOINQuery + " JOIN EventObjects as eo ON ao.art_object_id = eo.art_object_id WHERE eo.event_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, eventId);
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
