package com.spp.model.dataaccess;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private final Properties properties = new Properties();

    public DatabaseProperties() {
        properties.setProperty("url", "url:port/database");
        properties.setProperty("username", "username");
        properties.setProperty("password", "password");
    }

    public Properties getProperties() {
        return properties;
    }

    public void setDatabaseConfig(String url, String username, String password) {
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
    }

    public void saveProperties() throws FileNotFoundException {
        OutputStream propertiesFile = new FileOutputStream("db.properties");
        try {
            properties.store(propertiesFile, null);
        } catch (IOException exception) {
            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, exception.getMessage(), exception);
        }
    }

    public void loadProperties() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("db.properties");
        try {
            properties.load(inputStream);
            setDatabaseConfig(properties.getProperty("url"),
                    properties.getProperty("username"), properties.getProperty("password"));
        } catch (IOException exception) {
            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, exception.getMessage(), exception);
            saveProperties();
        }
    }
}
