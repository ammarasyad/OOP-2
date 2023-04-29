package com.tll.backend.datastore;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import com.tll.backend.datastore.DataStore.FileTypes;

public class DataStoreTest {

    @Test
    public void ValidTest() throws IOException {
        Barang barang = new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(), "test.com", true);

        DataStore.saveToJson(barang, "src/test/resources/barang.json");
        DataStore.saveToXml(barang, "src/test/resources/barang.xml");
        DataStore.saveToObj(barang, "src/test/resources/barang.obj");

        Barang load = (Barang) DataStore.load("src/test/resources/barang.json", FileTypes.JSON);
        System.out.println(load);
    }
}
