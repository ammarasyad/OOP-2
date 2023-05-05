package com.tll.backend.datastore.loader.sql;

import com.tll.backend.datastore.DataStore;
import com.tll.backend.datastore.loader.hikari.HikariConfig;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.Bill;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.javatuples.Pair;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class SqlAdapter implements DataStore {
    @Setter
    private String fileName; // Needed?
    private HikariConfig connection;

    @Override
    public void save(List<?> objects) throws IOException {
        try {
            createTablesIfNotExist(connection);

            if (objects.get(0) instanceof Barang) {
                insertKategori(connection, objects.stream().map(o -> ((Barang) o).getKategori()).toList());
                insertBarang(connection, objects);
            } else if (objects.get(0) instanceof Bill) {
                // THIS WILL INTRODUCE DUPLICATES. UNDER THE ASSUMPTION THAT DUPLICATE BILLS EXIST, IT WILL NOT BE HANDLED.
                insertCart(connection, objects);
                insertBill(connection, objects);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTablesIfNotExist(HikariConfig connection) throws SQLException {
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
                        ")",

                // 3. Bill
                """
                        CREATE TABLE IF NOT EXISTS Bill (
                            id INT NOT NULL,
                            user_id INT NOT NULL,
                            PRIMARY KEY (id)
                        )
                """,
                """
                        CREATE TABLE IF NOT EXISTS Cart (
                            id INT NOT NULL AUTO_INCREMENT,
                            id_barang INT NOT NULL,
                            jumlah INT NOT NULL,
                            id_bill INT NOT NULL,
                            PRIMARY KEY (id),
                            FOREIGN KEY (id_barang) REFERENCES Barang(id),
                            FOREIGN KEY (id_bill) REFERENCES Bill(id)
                        )
                """);

        try (Statement statement = connection.getDataSource().getConnection().createStatement()) {
            for (String s : queries) {
                statement.executeUpdate(s);
            }
        }
    }

    private void insertBarang(HikariConfig connection, List<?> objects) throws SQLException {
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

    private void insertKategori(HikariConfig connection, List<?> objects) throws SQLException {
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

    private void insertCart(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO Cart (id_barang, jumlah, id_bill) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query)) {
            objects.forEach(o -> {
                Bill bill = (Bill) o;
                bill.getCart().forEach(pair -> {
                    try {
                        statement.setInt(1, pair.getValue0().getId());
                        statement.setInt(2, pair.getValue1());
                        statement.setInt(3, bill.getId());
                        statement.executeUpdate();
                        statement.clearParameters();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
//            for (Object o : objects) {
//                Bill bill = (Bill) o;
//                for (Pair<Barang, Integer> pair : bill.getCart()) {
//                    statement.setInt(1, pair.getValue0().getId());
//                    statement.setInt(2, pair.getValue1());
//                    statement.setInt(3, bill.getId());
//                    statement.executeUpdate();
//                    statement.clearParameters();
//                }
//            }
        }
    }

    private void insertBill(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO Bill (id, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query)) {
            for (Object o : objects) {
                Bill bill = (Bill) o;
                statement.setInt(1, bill.getId());
                statement.setInt(2, bill.getUserId());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> load(Class<T> clazz) {
        try {
            if (clazz.equals(Barang.class)) {
                return (List<T>) loadBarang(connection);
            } else if (clazz.equals(Bill.class)) {
                return (List<T>) loadBill(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Class not supported");
    }

    private List<Barang> loadBarang(HikariConfig connection) throws SQLException {
        String query = "SELECT * FROM Barang";
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Barang> barangList = new ArrayList<>();

            while (resultSet.next()) {
                KategoriBarang kategoriBarang = unmarshalKategori(connection, resultSet.getInt("id_kategori"));

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

    private Barang loadSingleBarang(HikariConfig connection, Integer id) {
        String query = "SELECT * FROM Barang WHERE id = " + id;
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                KategoriBarang kategoriBarang = unmarshalKategori(connection, resultSet.getInt("id_kategori"));

                return Barang.builder()
                        .id(resultSet.getInt("id"))
                        .stok(resultSet.getInt("stok"))
                        .nama(resultSet.getString("nama"))
                        .harga(resultSet.getBigDecimal("harga"))
                        .hargaBeli(resultSet.getBigDecimal("harga_beli"))
                        .urlGambar(resultSet.getString("url_gambar"))
                        .kategori(kategoriBarang)
                        .dijual(resultSet.getBoolean("dijual"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private KategoriBarang unmarshalKategori(HikariConfig connection, int kategoriId) throws SQLException {
        KategoriBarang kategoriBarang = null;
        String kategoriQuery = "SELECT * FROM KategoriBarang WHERE id = " + kategoriId;
        try (PreparedStatement kategoriStatement = connection.getDataSource().getConnection().prepareStatement(kategoriQuery);
             ResultSet kategoriResult = kategoriStatement.executeQuery()) {
            if (kategoriResult.next()) {
                kategoriBarang = new KategoriBarang(kategoriResult.getInt("id"), kategoriResult.getString("nama"));
            }
        }
        return kategoriBarang;
    }

    private List<Bill> loadBill(HikariConfig connection) throws SQLException {
        String query = "SELECT * FROM Bill";
        try (PreparedStatement statement = connection.getDataSource().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Bill> billList = new ArrayList<>();

            while (resultSet.next()) {
                List<Pair<Barang, Integer>> pairs = new ArrayList<>();
                String cartQuery = "SELECT * FROM Cart WHERE id_bill = " + resultSet.getInt("id");
                List<Pair<Integer, Integer>> idAndJumlahList = new ArrayList<>();
                try (PreparedStatement cartStatement = connection.getDataSource().getConnection().prepareStatement(cartQuery);
                     ResultSet cartResult = cartStatement.executeQuery()) {

                    while (cartResult.next()) {
                        idAndJumlahList.add(Pair.with(cartResult.getInt("id_barang"), cartResult.getInt("jumlah")));
                    }
                }
                // Prevent repeated unnecessary queries
                AtomicReference<Barang> barang = new AtomicReference<>();
                idAndJumlahList.forEach(o -> {
                    Integer id = o.getValue0();
                    Integer jumlah = o.getValue1();
                    if (barang.get() == null || !barang.get().getId().equals(id)) {
                        barang.set(loadSingleBarang(connection, id));
                    }
                    pairs.add(new Pair<>(barang.get(), jumlah));
                });
                Bill bill = new Bill(resultSet.getInt("id"), resultSet.getInt("user_id"), pairs);
                billList.add(bill);
            }

            return billList;
        }
    }
}
