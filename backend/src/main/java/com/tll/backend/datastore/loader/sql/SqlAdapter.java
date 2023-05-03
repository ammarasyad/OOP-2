package com.tll.backend.datastore.loader.sql;

import com.fasterxml.jackson.databind.JavaType;
import com.tll.backend.datastore.DataStore;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SqlAdapter implements DataStore {
    @Setter
    private String fileName; // Needed?
    private SqlConnection connection;

    @Override
    public void save(List<?> objects) throws IOException {
        try {
            createTablesIfNotExist(connection);

            // For now barang sama kategori dulu
            if (objects.get(0) instanceof Barang) {
                insertKategori(connection, objects.stream().map(o -> ((Barang) o).getKategori()).toList());
                insertBarang(connection, objects);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTablesIfNotExist(SqlConnection connection) throws SQLException {
        List<String> queries = List.of(
                // 1. Kategori
                "CREATE TABLE IF NOT EXISTS KategoriBarang (" +
                        "id INT NOT NULL," +
                        "nama VARCHAR(255) UNIQUE NOT NULL," +
                        "PRIMARY KEY (id)" +
                        ")",

                // 2. Barang (ID dan Nama harus unik)
                "CREATE TABLE IF NOT EXISTS Barang (" +
                        "id INT NOT NULL AUTO_INCREMENT," +
                        "stok INT NOT NULL," +
                        "nama VARCHAR(255) UNIQUE NOT NULL ," +
                        "harga DECIMAL(10,2) NOT NULL," +
                        "harga_beli DECIMAL(10, 2) NOT NULL," +
                        "id_kategori INT NOT NULL," +
                        "url_gambar VARCHAR(255) NOT NULL," +
                        "dijual BOOLEAN NOT NULL," +
                        "PRIMARY KEY (id)," +
                        "FOREIGN KEY (id_kategori) REFERENCES KategoriBarang(id)" +
                        ")");

        try (Statement statement = connection.getDataSource().getConnection().createStatement()) {
            for (String s : queries) {
                statement.executeUpdate(s);
            }
        }
    }

    private void insertBarang(SqlConnection connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO barang (id, stok, nama, harga, harga_beli, id_kategori, url_gambar, dijual) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query)) {
            for (Object o : objects) {
                Barang barang = (Barang) o;
                statement.setInt(1, barang.getId());
                statement.setInt(2, barang.getStok());
                statement.setString(3, barang.getNama());
                statement.setBigDecimal(4, barang.getHarga());
                statement.setBigDecimal(5, barang.getHargaBeli());
                statement.setInt(6, barang.getKategori().getId());
                statement.setString(7, barang.getUrlGambar());
                statement.setBoolean(8, barang.isDijual());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    private void insertKategori(SqlConnection connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO KategoriBarang (id, nama) VALUES (?, ?)";
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query)) {
            for (Object o : objects) {
                KategoriBarang kategoriBarang = (KategoriBarang) o;
                statement.setInt(1, kategoriBarang.getId());
                statement.setString(2, kategoriBarang.getNamaKategori());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> load(JavaType clazz) {
        try {
            // Since clazz is a list, get the element type
            Class<?> elementClass = clazz.getContentType().getRawClass();

            if (elementClass.equals(Barang.class)) {
                return (List<T>) loadBarang(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Class not supported");
    }

    private List<Barang> loadBarang(SqlConnection connection) throws SQLException {
        String query = "SELECT * FROM Barang";
        try (Statement statement = connection.getDataSource().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<Barang> barangList = new ArrayList<>();

            while (resultSet.next()) {
                KategoriBarang kategoriBarang = null;
                try (Statement kategoriStatement = connection.getDataSource().getConnection().createStatement()) {
                    String kategoriQuery = "SELECT * FROM KategoriBarang WHERE id = " + resultSet.getInt("id_kategori");
                    try (ResultSet kategoriResult = kategoriStatement.executeQuery(kategoriQuery)) {
                        if (kategoriResult.next()) {
                            kategoriBarang = new KategoriBarang(kategoriResult.getInt("id"), kategoriResult.getString("nama"));
                        }
                    }
                }

                Barang barang = Barang.builder()
                        .id(resultSet.getInt("id"))
                        .stok(resultSet.getInt("stok"))
                        .nama(resultSet.getString("nama"))
                        .harga(resultSet.getBigDecimal("harga"))
                        .hargaBeli(resultSet.getBigDecimal("harga_beli"))
                        .urlGambar(resultSet.getString("url_gambar"))
                        .kategori(kategoriBarang)
                        .dijual(resultSet.getBoolean("dijual"))
                        .build();

                barangList.add(barang);
            }

            return barangList;
        }
    }
}
