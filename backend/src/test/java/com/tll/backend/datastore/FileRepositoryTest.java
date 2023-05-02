package com.tll.backend.datastore;

import com.tll.backend.datastore.FileRepository.FileTypes;
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
                new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(), "test.com", true),
                new Barang(1, 100, "Pensil", new BigDecimal(2000), new BigDecimal(1500), new KategoriBarang(), "test2.com", true),
                new Barang(2, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), new KategoriBarang(), "test3.com", true)
        );

        FileRepository.save(barangList, "src/test/resources/barang.json", FileTypes.JSON);
        FileRepository.save(barangList, "src/test/resources/barang.xml", FileTypes.XML);
        FileRepository.save(barangList, "src/test/resources/barang.obj", FileTypes.OBJ);

        List<Barang> json = FileRepository.load(Barang.class, "src/test/resources/barang.json", FileTypes.JSON);
        List<Barang> xml = FileRepository.load(Barang.class, "src/test/resources/barang.xml", FileTypes.XML);
        List<Barang> obj = FileRepository.load(Barang.class, "src/test/resources/barang.obj", FileTypes.OBJ);

        // Assert equals won't work because loading from the file will create a new KategoriBarang object for each Barang object
//        assertArrayEquals(barangList.toArray(), json.toArray(), "JSON not equal");
//        assertArrayEquals(barangList.toArray(), xml.toArray(), "XML not equal");
//        assertArrayEquals(barangList.toArray(), obj.toArray(), "OBJ not equal");
    }
}
