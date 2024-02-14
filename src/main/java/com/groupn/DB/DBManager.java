package com.groupn.DB;

import com.groupn.Entities.*;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {
    private final static String defaultDBFile = "./scripts/defaultDB.sql";
    private final static String defaultDataFile = "./scripts/defaultData.sql";

    private static void runScript(Connection connection, String scriptFilePath) {
        try {
            FileReader fileReader = new FileReader(scriptFilePath);
            RunScript.execute(connection, fileReader);
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefault(Connection connection) {
        try {
            runScript(connection, defaultDBFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setDefaultData(Connection connection) {
        try {
            runScript(connection, defaultDataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EventType getEventTypeById(Connection connection, int eventTypeId) {
        try {
            String sql = "SELECT * FROM EventType WHERE event_type_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, eventTypeId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    switch (resultSet.getInt("event_type_id")) {
                        case 1:
                            return EventType.Exhebition;
                        case 2:
                            return EventType.Wrongdoor;
                        default:
                            throw new Exception("Wrong Event Type");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Author getAuthorById(Connection connection, int authorId) {
        try {
            String sql = "SELECT * FROM Author WHERE author_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, authorId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return mapResultSetToAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Location getLocationById(Connection connection, int locationId) {
        try {
            String sql = "SELECT * FROM Location WHERE location_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, locationId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return mapResultSetToLocation(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addPurchase(Connection connection, Purchase purchase) {
        try {
            String sql = "INSERT INTO Purchase (date_of_purchase, price, art_object_id, seller_id, buyer_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace();
        }
    }

    private static Author mapResultSetToAuthor(ResultSet resultSet) throws SQLException {
        return new Author(
                resultSet.getInt("author_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("date_of_birth").toLocalDate(),
                new ArrayList<>() // You need to load artObjects separately
        );
    }

    private static Location mapResultSetToLocation(ResultSet resultSet) throws SQLException {
        return new Location(
                resultSet.getInt("location_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("date_of_opening").toLocalDate(),
                resultSet.getString("placement"),
                null,
                new ArrayList<>()
        );
    }

}
