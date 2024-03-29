package com.groupn.database;

import com.groupn.database.mapper.EntityMapper;
import com.groupn.database.sql_request.*;
import com.groupn.entities.*;
import lombok.Getter;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Getter
public class DBManager implements // implementing interfaces with specific sql requests for each entity
        ArtObjectSQL, OwnerSQL, AuthorSQL, PurchaseSQL, LocationSQL, EventTypeSQL, LocationTypeSQL, EventSQL,EventObjectsSQL {

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

    public boolean checkInjectionSafety(String userInput) {
        char[] unsafePattern = {'\'', '\"', '(', ')'};
        for (char ch : unsafePattern) {
            if (userInput.contains(String.valueOf(ch))) {
                return false;
            }
        }

        return true;
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

    public void addEvent(Event event) {
        addEvent(event, this);
    }

    public List<Event> getEventsByFilter(String filter) {
        return getEventsByFilter(filter, this);
    }

    public List<Author> getAuthorsByFilter(String filter) {
        return getAuthorsByFilter(filter, this);
    }

    //Author queries
    public Author getAuthorById(int authorId) {
        return getAuthorById(authorId, this);
    }

    public List<Author> getAllAuthors() {
        return getAllAuthors(this);
    }

    public void addAuthor(Author author) {
        addAuthor(author, this);
    }

    public Author getAuthorByArtObjectId(int artObjectId) {
        return getAuthorByArtObjectId(artObjectId, this);
    }

    //Location queries
    public Location getLocationById(int locationId) {
        return getLocationById(locationId, this);
    }

    public List<Location> getAllLocations() {
        return getAllLocation(this);
    }

    public List<Location> getLocationsByFilter(String filter) {
        return getLocationsByFilter(filter, this);
    }

    public void addLocation(Location location){
        addLocation( location, this);
    }
    public void updateLocation(Location location){
        updateLocation( location, this);
    }

    //Purchases queries
    public Purchase getPurchaseById(int purchaseId) {
        return getPurchaseId(purchaseId, this);
    }

    public List<Purchase> getAllPurchases() {
        return getAllPurchases(this);
    }

    public void addPurchase(Purchase purchase) {
        addPurchase(purchase, this);
    }

    public List<Purchase> getPurchasesByFilter(String filter ) {
        return getPurchasesByFilter(filter, this);
    }

    //Owner queries
    public Owner getOwnerById(int ownerId) {
        return getOwnerById(ownerId, this);
    }

    public List<Owner> getAllOwners() {
        return getAllOwners(this);
    }

    public List<Owner> getOwnersByFilter(String filter ) {
        return getOwnersByFilter(filter, this);
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

    public void addArtObject(ArtObject artObject) {
        addArtObject(artObject, this);
    }

    public void removeArtObjectById(int artObjectId) {
        removeArtObjectById(artObjectId, this);
    }

    public void updateArtObject(ArtObject artObject) {
        updateArtObject(artObject, this);
    }

    public List<ArtObject> getArtObjectsByFilter(String filter ) {
        return getArtObjectsByFilter(filter, this);
    }

    // new methods
    public void updateOwner(Owner owner) {
        updateOwner(owner, this);
    }


    public void removeLocation(int locationId) {
        removeLocation(locationId, this);
    }

    public void updateAuthor(Author author) {
        updateAuthor(author, this);
    }

    public void removeAuthor(int authorId) {
        removeAuthor(authorId, this);
    }

    public void removeEvent(int eventId) {
        removeEvent(eventId, this);
    }

    public void removePurchase(int purchaseId) {
        removePurchase(purchaseId, this);
    }

    public void updatePurchase(Purchase purchase) {
        updatePurchase(purchase, this);
    }

    public void removeOwner(int ownerId) {
        removeOwner(ownerId, this);
    }

    public void addOwner(Owner owner) {
        addOwner(owner, this);
    }

    public void updateEvent(Event event) {
        updateEvent(event, this);
    }

    public List<EventObjects> getAllEventObjects(){ return getAllEventObjects(this);}

    public void deleteEventObject(int eventId, int artObjectId) {deleteEventObject(eventId,artObjectId,this);}
}
