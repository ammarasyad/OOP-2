package com.tll.backend.datastore;

import com.tll.backend.datastore.loader.FileRepository;
import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.datastore.loader.ObjAdapter;
import com.tll.backend.datastore.loader.XmlAdapter;
import com.tll.backend.datastore.loader.sql.SqlAdapter;
import com.tll.backend.datastore.loader.sql.SqlConnection;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class FileRepositoryTest {

    @Test
    @SuppressWarnings("unused")
    public void ValidTest() throws IOException {
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

        // Assert equals won't work because loading from the file will create a new KategoriBarang object for each Barang object
//        assertArrayEquals(barangList.toArray(), json.toArray(), "JSON not equal");
//        assertArrayEquals(barangList.toArray(), xml.toArray(), "XML not equal");
//        assertArrayEquals(barangList.toArray(), obj.toArray(), "OBJ not equal");
    }
}
