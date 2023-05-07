package com.tll.gui.data;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class DataHandlerTest {

    @Test
    public void RepositoryTest() throws IOException {
        BarangRepository barangRepository = new BarangRepository();
        FixedBillRepository fixedBillRepository = new FixedBillRepository();
        TemporaryBillRepository temporaryBillRepository = new TemporaryBillRepository();

        Barang barang = new Barang(2, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true);

        barangRepository.save(barang);
        fixedBillRepository.save(new FixedBill(1, 2, List.of(Pair.with(barang, 2))));
        temporaryBillRepository.save(new TemporaryBill(1));

//        DataHandler.save(barangRepository, "test", DataHandler.FileTypes.JSON);
//        DataHandler.save(fixedBillRepository, "tetxml", DataHandler.FileTypes.XML);
//        DataHandler.save(temporaryBillRepository, "object", DataHandler.FileTypes.OBJ);
//
//        BarangRepository barangLoad = DataHandler.load(BarangRepository.class, "test", DataHandler.FileTypes.JSON);
//        FixedBillRepository billLoad = DataHandler.load(FixedBillRepository.class, "tetxml", DataHandler.FileTypes.XML);
//        TemporaryBillRepository tempLoad = DataHandler.load(TemporaryBillRepository.class, "object", DataHandler.FileTypes.OBJ);
    }
}
