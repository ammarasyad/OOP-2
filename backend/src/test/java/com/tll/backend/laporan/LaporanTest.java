package com.tll.backend.laporan;

import com.tll.backend.laporan.Laporan;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LaporanTest {
    @Test
    public void ValidTest() {
        List<Pair<Barang, Integer>> listBarang = new ArrayList<>();

        listBarang.add(Pair.with(new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), null, "test.com", true), 2));
        listBarang.add(Pair.with(new Barang(1, 100, "Pensil", new BigDecimal(2000), new BigDecimal(1500), null, "test2.com", true), 3));
        listBarang.add(Pair.with(new Barang(2, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), null, "test3.com", true), 1));

        FixedBill fixedBill = new FixedBill(listBarang);
        Laporan laporan = new Laporan("sample2.pdf", fixedBill);
        laporan.save();
    }

    @Test
    public void NullTest() {
        Laporan laporan = new Laporan("sample2.pdf", null);
        laporan.save();
    }
}
