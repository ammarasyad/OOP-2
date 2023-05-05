package com.tll.backend.datastore.loader.hikari;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

// Enum singleton for thread-safe connection pooling
public enum HikariConfig implements AutoCloseable {
    INSTANCE;

    // For now, the property file is hardcoded
    private static final String PROPERTY_FILE_PATH = "src/main/resources/hikari.properties";

    // I think it's better to use Properties, but I'll change it later
    private static final com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig(PROPERTY_FILE_PATH);
    private static HikariDataSource dataSource;

    static {
        if (dataSource == null) {
            dataSource = new HikariDataSource(config);
        }
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
