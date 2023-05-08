package com.tll.backend.datastore.loader.sql;

import com.tll.backend.datastore.DataStore;
import com.tll.backend.datastore.loader.hikari.HikariConfig;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import org.javatuples.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.tll.backend.datastore.TableDropper.dropTable;

public class SqlAdapter implements DataStore {
    private final HikariConfig connection;

    public SqlAdapter(HikariConfig connection) {
        this.connection = connection;
        try {
            dropTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(List<?> objects) throws IOException {
        try {
            createTablesIfNotExist(connection);

            if (objects.get(0) instanceof Barang) {
                insertKategori(connection, objects.stream().map(o -> ((Barang) o).getKategori()).toList());
                insertBarang(connection, objects);
            } else if (objects.get(0) instanceof FixedBill) {
                // THIS WILL INTRODUCE DUPLICATES. UNDER THE ASSUMPTION THAT DUPLICATE BILLS EXIST, IT WILL NOT BE HANDLED.
                insertFixedBill(connection, objects);
                insertCart(connection, objects);
            } else if (objects.get(0) instanceof TemporaryBill) { // Temporary bill saved to persistent storage?????
                insertTemporaryBill(connection, objects);
                insertTemporaryCart(connection, objects);
            } else if (objects.get(0) instanceof Customer) {
                insertFixedBill(connection, objects.stream().map(o -> ((Customer) o).getBill()).toList());
                insertCart(connection, objects.stream().map(o -> ((Customer) o).getBill()).toList());
                insertCustomer(connection, objects);
            } else if (objects.get(0) instanceof Member) {
                insertKategori(connection, objects.stream()
                        .flatMap(o -> ((Member) o).getBills().stream())
                        .flatMap(e -> e.getCart().stream())
                        .map(l -> l.getValue0().getKategori()).toList());
                insertBarang(connection, objects.stream()
                        .flatMap(o -> ((Member) o).getBills().stream())
                        .flatMap(e -> e.getCart().stream())
                        .map(Pair::getValue0).toList());
                insertFixedBill(connection, objects.stream().flatMap(o -> ((Member) o).getBills().stream()).toList());
                insertCart(connection, objects.stream().flatMap(o -> ((Member) o).getBills().stream()).toList());
                insertMember(connection, objects);
                insertMemberBills(connection, objects);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTemporaryBill(HikariConfig connection, Integer id) throws SQLException {
        String query = "DELETE FROM TemporaryBill WHERE id = ?";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static void createTablesIfNotExist(HikariConfig connection) throws SQLException {
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

                // 3. FixedBill
                """
                        CREATE TABLE IF NOT EXISTS FixedBill (
                            id INT NOT NULL,
                            id_user INT NOT NULL,
                            PRIMARY KEY (id)
                        )
                """,
                """
                        CREATE TABLE IF NOT EXISTS Cart (
                            id INT NOT NULL AUTO_INCREMENT,
                            id_barang INT NOT NULL,
                            jumlah INT NOT NULL,
                            id_bill INT NOT NULL,
                            PRIMARY KEY (id_barang, id_bill),
                            FOREIGN KEY (id_barang) REFERENCES Barang(id),
                            FOREIGN KEY (id_bill) REFERENCES FixedBill(id)
                        )
                """,

                // 4. TemporaryBill
                """
                        CREATE TABLE IF NOT EXISTS TemporaryBill (
                            id INT NOT NULL,
                            PRIMARY KEY (id)
                        )
                """,
                """
                        CREATE TABLE IF NOT EXISTS TemporaryCart (
                            id INT NOT NULL AUTO_INCREMENT UNIQUE,
                            id_barang INT NOT NULL,
                            jumlah INT NOT NULL,
                            id_bill INT NOT NULL,
                            PRIMARY KEY (id_barang, id_bill),
                            FOREIGN KEY (id_barang) REFERENCES Barang(id),
                            CONSTRAINT `fk_temporary_cart_bill`
                                FOREIGN KEY (id_bill) REFERENCES TemporaryBill(id)
                                ON DELETE CASCADE
                                ON UPDATE RESTRICT
                        )
                """

                ,
                // 5. Customer
                """
                        CREATE TABLE IF NOT EXISTS Customer (
                            id INT NOT NULL,
                            id_bill INT NOT NULL,
                            PRIMARY KEY (id),
                            FOREIGN KEY (id_bill) REFERENCES FixedBill(id)
                        )
                """,

                // 6. Member
                """
                        CREATE TABLE IF NOT EXISTS Member (
                            id INT NOT NULL,
                            type VARCHAR(255) NOT NULL,
                            active BOOLEAN NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            phone VARCHAR(255) NOT NULL,
                            point BIGINT NOT NULL,
                            PRIMARY KEY (id)
                        )
                """,
                """
                        CREATE TABLE IF NOT EXISTS MemberBills (
                            id_member INT NOT NULL,
                            id_bill INT NOT NULL,
                            PRIMARY KEY (id_member, id_bill),
                            FOREIGN KEY (id_member) REFERENCES Member(id),
                            FOREIGN KEY (id_bill) REFERENCES FixedBill(id)
                        )
                """);

        try (Statement statement = connection.getDataSource().getConnection().createStatement()) {
            for (String s : queries) {
                statement.executeUpdate(s);
            }
        }
    }

    private void insertBarang(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO Barang (id, stok, nama, harga, harga_beli, id_kategori, url_gambar, dijual) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connection.getDataSource().getConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {
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
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
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
        try (Connection conn = connection.getDataSource().getConnection()) {
            objects.forEach(o -> {
                FixedBill bill = (FixedBill) o;
                bill.getCart().forEach(pair -> {
                    try (PreparedStatement statement = conn.prepareStatement(query)) {
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
        }
    }

    private void insertFixedBill(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO FixedBill (id, id_user) VALUES (?, ?)";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            for (Object o : objects) {
                FixedBill fixedBill = (FixedBill) o;
                statement.setInt(1, fixedBill.getId());
                statement.setInt(2, fixedBill.getUserId());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    private void insertTemporaryCart(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO TemporaryCart (id_barang, jumlah, id_bill) VALUES (?, ?, ?)";
        try (Connection conn = connection.getDataSource().getConnection()) {
            objects.forEach(o -> {
                TemporaryBill tempBill = (TemporaryBill) o;
                tempBill.getCart().forEach(pair -> {
                    try (PreparedStatement statement = conn.prepareStatement(query)) {
                        statement.setInt(1, pair.getValue0().getId());
                        statement.setInt(2, pair.getValue1());
                        statement.setInt(3, tempBill.getId());
                        statement.executeUpdate();
                        statement.clearParameters();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        }
    }

    private void insertTemporaryBill(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO TemporaryBill (id) VALUES (?)";
        try (Connection conn = connection.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            for (Object o : objects) {
                statement.setInt(1, ((TemporaryBill) o).getId());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    private void insertCustomer(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO Customer (id, id_bill) VALUES (?, ?)";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            for (Object o : objects) {
                Customer customer = (Customer) o;
                statement.setInt(1, customer.getId());
                statement.setInt(2, customer.getBill().getId());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    private void insertMember(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO Member (id, type, active, name, phone, point) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            for (Object o : objects) {
                Member member = (Member) o;
                statement.setInt(1, member.getId());
                statement.setString(2, member.getType());
                statement.setBoolean(3, member.isActiveStatus());
                statement.setString(4, member.getName());
                statement.setString(5, member.getPhone());
                statement.setLong(6, member.getPoint());
                statement.executeUpdate();
                statement.clearParameters();
            }
        }
    }

    private static void insertMemberBills(HikariConfig connection, List<?> objects) throws SQLException {
        String query = "INSERT IGNORE INTO MemberBills (id_member, id_bill) VALUES (?, ?)";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            for (Object o : objects) {
                Member member = (Member) o;
                member.getBills().forEach(bill -> {
                    try {
                        statement.setInt(1, member.getId());
                        statement.setInt(2, bill.getId());
                        statement.executeUpdate();
                        statement.clearParameters();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> load(Class<T> clazz) {
        try {
            if (clazz.equals(Barang.class)) {
                return (List<T>) loadBarang(connection);
            } else if (clazz.equals(FixedBill.class)) {
                return (List<T>) loadBill(connection);
            } else if (clazz.equals(Customer.class)) {
                return (List<T>) loadCustomer(connection);
            } else if (clazz.equals(Member.class)) {
                return (List<T>) loadMember(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Class not supported");
    }

    private List<Barang> loadBarang(HikariConfig connection) throws SQLException {
        String query = "SELECT * FROM Barang";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
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
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
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
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(kategoriQuery);
             ResultSet kategoriResult = statement.executeQuery()) {
            if (kategoriResult.next()) {
                kategoriBarang = new KategoriBarang(kategoriResult.getInt("id"), kategoriResult.getString("nama"));
            }
        }
        return kategoriBarang;
    }

    private List<FixedBill> loadBill(HikariConfig connection) throws SQLException {
        String query = "SELECT * FROM FixedBill";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<FixedBill> billList = new ArrayList<>();

            while (resultSet.next()) {
                List<Pair<Barang, Integer>> pairs = loadCart(connection, resultSet.getInt("id"));
                FixedBill bill = new FixedBill(resultSet.getInt("id"), resultSet.getInt("id_user"), pairs);
                billList.add(bill);
            }

            return billList;
        }
    }

    private List<Pair<Barang, Integer>> loadCart(HikariConfig connection, Integer idBill) throws SQLException {
        String query = "SELECT * FROM Cart WHERE id_bill = " + idBill;
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Pair<Barang, Integer>> pairs = new ArrayList<>();

            while (resultSet.next()) {
                Barang barang = loadSingleBarang(connection, resultSet.getInt("id_barang"));
                pairs.add(new Pair<>(barang, resultSet.getInt("jumlah")));
            }

            return pairs;
        }
    }

    private List<Customer> loadCustomer(HikariConfig connection) throws SQLException {
        String query = "SELECT * FROM Customer";
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Customer> customerList = new ArrayList<>();

            while (resultSet.next()) {
                String billQuery = "SELECT * FROM FixedBill WHERE id = " + resultSet.getInt("id_bill");
                FixedBill fixedBill = null;
                try (Connection billConn = connection.getDataSource().getConnection();
                     PreparedStatement billStatement = billConn.prepareStatement(billQuery);
                     ResultSet billResult = billStatement.executeQuery()) {
                    if (billResult.next()) {
                        fixedBill = new FixedBill(billResult.getInt("id"), resultSet.getInt("id"), loadCart(connection, billResult.getInt("id")));
                    }
                }
                Customer customer = new Customer(resultSet.getInt("id"), fixedBill);

                customerList.add(customer);
            }

            return customerList;
        }
    }

    private List<Member> loadMember(HikariConfig connection) throws SQLException {
        String query = "SELECT * FROM Member";
        List<Member> memberList = new ArrayList<>();
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                memberList.add(Member.builder()
                        .id(resultSet.getInt("id"))
                        .type(resultSet.getString("type"))
                        .activeStatus(resultSet.getBoolean("active"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .bills(new ArrayList<>())
                        .point(resultSet.getLong("point"))
                        .build());

            }
        }

        query = "SELECT * FROM MemberBills WHERE id_member IN " + memberList.stream().map(Member::getId).toList();
        query = query.replace("[", "(");
        query = query.replace("]", ")");

        List<Pair<Integer, Integer>> idBills = new ArrayList<>();
        try (Connection conn = connection.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                idBills.add(Pair.with(resultSet.getInt("id_member"), resultSet.getInt("id_bill")));
            }
        }

        List<FixedBill> bills = loadBill(connection);
        memberList.forEach(member ->
                member.setBills(bills.stream().filter(bill -> idBills.stream().anyMatch(idBill -> idBill.getValue1().equals(bill.getId())
                        && idBill.getValue0().equals(member.getId()))).toList()));

        return memberList;
    }
}
