package com.groupn.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String JDBC_URL = "jdbc:h2:file:./db/library;DB_CLOSE_ON_EXIT=FALSE;USER=group_n;PASSWORD=qwerty_is_safe_password";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

}