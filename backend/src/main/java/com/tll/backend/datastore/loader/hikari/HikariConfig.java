package com.tll.backend.datastore.loader.hikari;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

// Enum singleton for thread-safe connection pooling
public enum HikariConfig implements AutoCloseable {
    INSTANCE;

    private static final com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig(getProperties());
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

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("username", "oop");
        properties.setProperty("password", "oop");
        properties.setProperty("jdbcUrl", "jdbc:mariadb://ec2-52-221-241-44.ap-southeast-1.compute.amazonaws.com:3306/oop");

        return properties;
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
