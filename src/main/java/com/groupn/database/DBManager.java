package com.groupn.database;

import com.groupn.database.mapper.EntityMapper;
import com.groupn.database.sql_request.*;
import com.groupn.entities.*;
import lombok.Getter;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Getter
public class DBManager implements // implementing interfaces with specific sql requests for each entity
        ArtObjectSQL, OwnerSQL, AuthorSQL, PurchaseSQL, LocationSQL, EventTypeSQL, LocationTypeSQL, EventSQL {

    private EntityMapper mapper; // class which converts from ResultSet to Entity classes

    private final Logger logger = Logger.getLogger(DBManager.class.getName()); // for logs

    private Connection connection; // class which creates connection with database

    private final String defaultDBFile = "./scripts/defaultDB.sql"; // default .sql file with commands for creating database

    private final String defaultDataFile = "./scripts/defaultData.sql"; // default .sql file for adding data into database

    public void createConnection(String url) {
        try {
            this.connection = DBConnector.connect(url);
            mapper = new EntityMapper(this);
            logger.info("Connection with database is created.");
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
            logger.info("Connection with database is closed.");
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    private void runScript(Connection connection, String scriptFilePath) {
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
        return getLocationTypeById(locationTypeId, this);
    }

    //EventType queries
    public EventType getEventTypeById(int eventTypeId) {
        return getEventTypeById(eventTypeId, this);
    }

    //Event queries
    public Event getEventById(int eventId) {
        return getEventById(eventId, this);
    }

    public List<Event> getAllEvents() {
        return getAllEvents(this);
    }

    //Author queries
    public Author getAuthorById(int authorId) {
        return getAuthorById(authorId, this);
    }

    public List<Author> getAllAuthors() {
        return getAllAuthors(this);
    }

    //Location queries
    public Location getLocationById(int locationId) {
        return getLocationById(locationId, this);
    }

    public List<Location> getAllLocations() {
        return getAllLocation(this);
    }

    //Purchases queries
    public Purchase getPurchaseById(int purchaseId) {
        return getPurchaseId(purchaseId, this);
    }

    public List<Purchase> getAllPurchases(){return getAllPurchases(this);}

    public void addPurchase(Purchase purchase) {
        addPurchase(purchase, this);
    }

    //Owner queries
    public Owner getOwnerById(int ownerId) {
        return getOwnerById(ownerId, this);
    }

    public List<Owner> getAllOwners() {
        return getAllOwners(this);
    }

    //ArtObject queries
    public ArtObject getArtObjectById(int artObjectId) {
        return getArtObjectById(artObjectId, this);
    }

    public List<ArtObject> getArtObjectByOwner(int ownerId) {
        return getArtObjectByOwner(ownerId, this);
    }

    public List<ArtObject> getArtObjectByAuthor(int authorId) {
        return getArtObjectByAuthor(authorId, this);
    }

    public List<ArtObject> getArtObjectByLocation(int locationId) {
        return getArtObjectByLocation(locationId, this);
    }

    public List<ArtObject> getAllArtObjects() {
        return getAllArtObjects(this);
    }
}
