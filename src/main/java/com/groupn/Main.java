package com.groupn;

import com.groupn.DB.DBConnector;
import com.groupn.DB.DBManager;
import com.groupn.Entities.Author;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Example of work with DBManager

        DBManager manager = new DBManager();
        try {

            manager.createConnection(DBConnector.JDBC_URL); // connect to JDBC_URL
            manager.setDefault(); // create tables
            manager.setDefaultData(); // add elements into tables
            /*

                Start work with DB

             */
            Author author = manager.getAuthorById(1);
            System.out.println(author);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        manager.closeConnection();
    }
}