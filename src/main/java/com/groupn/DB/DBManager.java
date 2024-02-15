package com.groupn.DB;

import com.groupn.Entities.*;
import lombok.Getter;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

@Getter
public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class.getName());

    private Connection connection;

    private final static String defaultDBFile = "./scripts/defaultDB.sql";

    private final static String defaultDataFile = "./scripts/defaultData.sql";

    public void createConnection(String url){
        try {
            this.connection = DBConnector.connect(url);
            logger.info("Connection with database is created.");
        }catch(SQLException e){
            logger.severe("Error: " + e.getMessage());
        }
    }

    public void closeConnection(){
        try {
            this.connection.close();
            logger.info("Connection with database is closed.");
        }catch(SQLException e){
            logger.severe("Error: " + e.getMessage());
        }
    }

    private static void runScript(Connection connection, String scriptFilePath) {
        try {
            FileReader fileReader = new FileReader(scriptFilePath);
            RunScript.execute(connection, fileReader);
            fileReader.close();
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    public void setDefault() {
        try {
            runScript(this.connection, defaultDBFile);
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }
    }


    public void setDefaultData() {
        try {
            runScript(this.connection, defaultDataFile);
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    //LocationType queries
    public LocationType getLocationTypeById(int locationTypeId) {
        try {
            String sql = "SELECT * FROM LocationType WHERE location_type_id = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, locationTypeId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    switch (resultSet.getInt("location_type_id")) {
                        case 1:
                            return LocationType.MUSEUM;
                        case 2:
                            return LocationType.PRIVATE_LOCATION;
                        default:
                            throw new Exception("Wrong Location Type");
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }
        return null;
    }

    //EventType queries
    public EventType getEventTypeById( int eventTypeId) {
        try {
            String sql = "SELECT * FROM EventType WHERE event_type_id = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, eventTypeId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    switch (resultSet.getInt("event_type_id")) {
                        case 1:
                            return EventType.EXHEBITION;
                        case 2:
                            return EventType.WRONGDOOR;
                        default:
                            throw new Exception("Wrong Event Type");
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }
        return null;
    }

    //Author queries
    public Author getAuthorById( int authorId) {
        try {
            String sql = "SELECT * FROM Author WHERE author_id = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, authorId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return mapResultSetToAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
        return null;
    }

    //Location queries
    public Location getLocationById( int locationId) {
        try {
            String sql = "SELECT * FROM Location WHERE location_id = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, locationId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return mapResultSetToLocation(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
        return null;
    }

    //Purchases queries
    public void addPurchase( Purchase purchase) {
        try {
            String sql = "INSERT INTO Purchase (date_of_purchase, price, art_object_id, seller_id, buyer_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
            logger.severe("Error: " + e.getMessage());
        }
    }

    //Event queries

    //Owner queries

    //ArtObject queries
    public ArtObject getArtObjectById(int artObjectId) {
        try {
            String sql = "SELECT * FROM ArtObject WHERE art_object_id = ?";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, artObjectId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return mapResultSetToArtObject(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
        return null;
    }

    private Author mapResultSetToAuthor(ResultSet resultSet) throws SQLException {
        return new Author(
                resultSet.getInt("author_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("date_of_birth").toLocalDate(),
                new ArrayList<>()
        );
    }

    private Location mapResultSetToLocation(ResultSet resultSet) throws SQLException {
        return new Location(
                resultSet.getInt("location_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("date_of_opening").toLocalDate(),
                resultSet.getString("placement"),
                getLocationTypeById(resultSet.getInt("location_type_id")),
                new ArrayList<>()
        );
    }

    private ArtObject mapResultSetToArtObject(ResultSet resultSet) throws SQLException {
        ArtObject artObject = new ArtObject(
        resultSet.getInt("art_object_id"),
        resultSet.getString("art_object_name"),
        resultSet.getString("art_object_description"),
        getAuthorById(resultSet.getInt("author_id")),
        null,//resultSet.getInt("current_owner_id")
        getLocationById(resultSet.getInt("current_location_id")),
        resultSet.getDate("date").toLocalDate(),null,null);

        return artObject;
    }

}
