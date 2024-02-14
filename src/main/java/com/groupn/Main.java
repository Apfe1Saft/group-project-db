package com.groupn;

import com.groupn.DB.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = DBConnector.connect();
            System.out.println("Connected to the database");
            //DBConnector.setDefault(connection);
            // else requests
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}