package com.groupn;

import com.groupn.database.DBConnector;
import com.groupn.database.DBManager;
import com.groupn.entities.*;

import com.groupn.App.MainInterface;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Example of work with DBManager

        DBManager manager = new DBManager();

        // run main interface class...

        try {

            manager.createConnection(DBConnector.JDBC_URL); // connect to JDBC_URL
            manager.setDefault(); // create tables
            manager.setDefaultData(); // add elements into tables

            //manager.removeAuthor(1);
            //System.out.println("==>" + manager.getArtObjectById(1));

            // Launch the UI
            SwingUtilities.invokeLater(() -> new MainInterface(manager)); // Comment out to skip ui

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}