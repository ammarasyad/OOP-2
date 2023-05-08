package com.tll.backend.datastore;

import com.tll.backend.datastore.loader.hikari.HikariConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class TableDropper {
    public static void dropTable(HikariConfig hikariConfig) throws SQLException {
        List<String> queries = List.of(
                "DROP TABLE IF EXISTS KategoriBarang CASCADE",
                "DROP TABLE IF EXISTS Barang CASCADE",
                "DROP TABLE IF EXISTS Customer CASCADE",
                "DROP TABLE IF EXISTS FixedBill CASCADE",
                "DROP TABLE IF EXISTS TemporaryBill CASCADE",
                "DROP TABLE IF EXISTS Cart CASCADE",
                "DROP TABLE IF EXISTS TemporaryCart CASCADE",
                "DROP TABLE IF EXISTS Member CASCADE",
                "DROP TABLE IF EXISTS MemberBills CASCADE"
        );

        try (Connection connection = hikariConfig.getDataSource().getConnection();
            Statement statement = connection.createStatement()) {
            for (String query : queries) {
                statement.execute(query);
            }
        }
    }
}
