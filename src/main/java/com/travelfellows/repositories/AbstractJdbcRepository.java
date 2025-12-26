package com.travelfellows.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractJdbcRepository {

    protected final Properties properties;
    protected final String url;

    public AbstractJdbcRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        url = properties.getProperty("url");
    }
}
