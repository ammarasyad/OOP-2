package com.tll.backend.datastore;

import com.tll.backend.datastore.loader.FileRepository;
import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.datastore.loader.ObjAdapter;
import com.tll.backend.datastore.loader.XmlAdapter;
import com.tll.backend.datastore.loader.sql.SqlAdapter;
import com.tll.backend.datastore.loader.sql.SqlConnection;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.Bill;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("unused")
public class FileRepositoryTest {
    // Test these one by one. Running them all will cause subsequent tests to fail due to closed SQL connection.

    @Test
    public void BarangTest() throws IOException {
        List<Barang> barangList = List.of(
                new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true),
                new Barang(2, 100, "Pensil", new BigDecimal(2000), new BigDecimal(900), new KategoriBarang(1, "kat2"), "test2.com", true),
                new Barang(3, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), new KategoriBarang(2, "kat3"), "test3.com", true)
        );

        JsonAdapter jsonAdapter = new JsonAdapter("src/test/resources/barang.json");
        XmlAdapter xmlAdapter = new XmlAdapter("src/test/resources/barang.xml");
        ObjAdapter objAdapter = new ObjAdapter("src/test/resources/barang.obj");

        FileRepository.save(barangList, jsonAdapter);
        FileRepository.save(barangList, xmlAdapter);
        FileRepository.save(barangList, objAdapter);

        List<Barang> json = FileRepository.load(Barang.class, jsonAdapter);
        List<Barang> xml = FileRepository.load(Barang.class, xmlAdapter);
        List<Barang> obj = FileRepository.load(Barang.class, objAdapter);

        List<Barang> sql;
        try (SqlConnection connection = SqlConnection.INSTANCE) {
            SqlAdapter sqlAdapter = new SqlAdapter("src/test/resources/barang.sql", connection);
            FileRepository.save(barangList, sqlAdapter);
            sql = FileRepository.load(Barang.class, sqlAdapter);
        }

        json.forEach(o -> assertEquals(o.getClass(), Barang.class, "JSON not equal"));
        xml.forEach(o -> assertEquals(o.getClass(), Barang.class, "XML not equal"));
        obj.forEach(o -> assertEquals(o.getClass(), Barang.class, "OBJ not equal"));
        sql.forEach(o -> assertEquals(o.getClass(), Barang.class, "SQL not equal"));
    }

//    @Test
    public void BillTest() throws IOException {
        Barang barang = new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true);
        List<Barang> barangList = List.of(barang);

        List<Bill> billList = List.of(
                new Bill(1, 2, List.of(Pair.with(barang, 2), Pair.with(barang, 3))),
                new Bill(2, 9, List.of(Pair.with(barang, 1), Pair.with(barang, 4))),
                new Bill(3, 1, List.of(Pair.with(barang, 5), Pair.with(barang, 6)))
        );

        JsonAdapter jsonAdapter = new JsonAdapter("src/test/resources/bill.json");
        XmlAdapter xmlAdapter = new XmlAdapter("src/test/resources/bill.xml");
        ObjAdapter objAdapter = new ObjAdapter("src/test/resources/bill.obj");

        FileRepository.save(billList, jsonAdapter);
        FileRepository.save(billList, xmlAdapter);
        FileRepository.save(billList, objAdapter);

        List<Bill> json = FileRepository.load(Bill.class, jsonAdapter);
        List<Bill> xml = FileRepository.load(Bill.class, xmlAdapter);
        List<Bill> obj = FileRepository.load(Bill.class, objAdapter);

        List<Bill> sql;
        try (SqlConnection connection = SqlConnection.INSTANCE) {
            SqlAdapter sqlAdapter = new SqlAdapter("src/test/resources/bill.sql", connection);
            FileRepository.save(barangList, sqlAdapter);
            FileRepository.save(billList, sqlAdapter);
            sql = FileRepository.load(Bill.class, sqlAdapter);
        }

        json.forEach(o -> assertEquals(o.getClass(), Bill.class, "JSON not equal"));
        xml.forEach(o -> assertEquals(o.getClass(), Bill.class, "XML not equal"));
        obj.forEach(o -> assertEquals(o.getClass(), Bill.class, "OBJ not equal"));
        sql.forEach(o -> assertEquals(o.getClass(), Bill.class, "SQL not equal"));
    }
}
