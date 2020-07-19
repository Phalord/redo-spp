package com.spp.utils;

import com.spp.model.dataaccess.DatabaseProperties;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
    private final DatabaseProperties databaseProperties = new DatabaseProperties();
    private Connection connection = null;
    private String database;
    private String username;
    private String password;

    public MySQLConnection() {
        try {
            loadProperties();
        } catch (FileNotFoundException exception) {
            Logger.getLogger(MySQLConnection.class.getName())
                    .log(Level.SEVERE, exception.getMessage(), exception);
        }
    }

    public Connection getConnection() throws SQLException {
        connect();
        return connection;
    }

    private void setDatabase(String database) {
        this.database = database;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void loadProperties() throws FileNotFoundException {
        databaseProperties.loadProperties();
        Properties properties = databaseProperties.getProperties();
        setDatabase(properties.getProperty("url"));
        setUsername(properties.getProperty("username"));
        setPassword(properties.getProperty("password"));
    }

    private void connect() throws SQLException {
        String driver = "jdbc:mysql://";
        connection = DriverManager.getConnection(String.format("%s%s%s",
                driver, database,"?zeroDateTimeBehavior=convertToNull"), username, password);
    }
}
