package com.tll.backend.datastore.loader.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// Enum singleton for thread-safe connection pooling
public enum SqlConnection implements AutoCloseable {
    INSTANCE;

    // For now, the property file is hardcoded
    private static final String PROPERTY_FILE_PATH = "src/main/resources/hikari.properties";

    // I think it's better to use Properties, but I'll change it later
    private static final HikariConfig config = new HikariConfig(PROPERTY_FILE_PATH);
    private static HikariDataSource dataSource;

    static {
        if (dataSource == null) {
            dataSource = new HikariDataSource(config);
        }
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
