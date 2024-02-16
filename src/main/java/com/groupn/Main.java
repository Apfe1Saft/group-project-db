package com.groupn;

import com.groupn.database.DBConnector;
import com.groupn.database.DBManager;
import com.groupn.entities.*;

public class Main {

    public static void main(String[] args) {

        // Example of work with DBManager

        DBManager manager = new DBManager();

        // run main interface class...

        try {

            manager.createConnection(DBConnector.JDBC_URL); // connect to JDBC_URL
            manager.setDefault(); // create tables
            manager.setDefaultData(); // add elements into tables

            Author author = manager.getAuthorById(1);
            ArtObject artObject = manager.getArtObjectById(1);
            Location location = manager.getLocationById(1);
            Owner owner = manager.getOwnerById(1);
            Purchase purchase = manager.getPurchaseById(1);
            Event event = manager.getEventById(1);

            System.out.println(author);
            System.out.println(artObject);
            System.out.println(location);
            System.out.println(owner);
            System.out.println(purchase);
            System.out.println(event);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        manager.closeConnection();
    }
}