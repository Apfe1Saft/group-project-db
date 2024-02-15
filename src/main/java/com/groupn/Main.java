package com.groupn;

import com.groupn.database.DBConnector;
import com.groupn.database.DBManager;
import com.groupn.entities.*;

public class Main {

    public static void main(String[] args) {

        // Example of work with DBManager

        DBManager manager = new DBManager();
        try {

            manager.createConnection(DBConnector.JDBC_URL); // connect to JDBC_URL
            manager.setDefault(); // create tables
            manager.setDefaultData(); // add elements into tables

            Author author = manager.getAuthorById(1);
            ArtObject artObject = manager.getArtObjectById(1);
            Location location = manager.getLocationById(1);
            Owner owner = manager.getOwnerById(1);
            Purchase purchase = manager.getPurchase(1);

            System.out.println(author);
            System.out.println(artObject);
            System.out.println(location);
            System.out.println(owner);
            System.out.println(purchase);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        manager.closeConnection();
    }
}