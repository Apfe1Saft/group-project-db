package com.groupn.database;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Setter
public class DBConnector {
    public static final String JDBC_URL = "jdbc:h2:file:./db/library;DB_CLOSE_ON_EXIT=FALSE;USER=group_n;PASSWORD=qwerty_is_safe_password";

    public static Connection connect(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

}