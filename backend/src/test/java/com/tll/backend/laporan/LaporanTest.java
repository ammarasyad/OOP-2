package com.tll.backend.laporan;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.FixedBill;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Execution(ExecutionMode.CONCURRENT)
public class LaporanTest {

//    private static final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//
//    @Test
//    public void ValidTest() throws ExecutionException, InterruptedException {
//        List<Pair<Barang, Integer>> listBarang = new ArrayList<>();
//
//        listBarang.add(Pair.with(new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(), "test.com", true), 2));
//        listBarang.add(Pair.with(new Barang(1, 100, "Pensil", new BigDecimal(2000), new BigDecimal(1500), new KategoriBarang(), "test2.com", true), 3));
//        listBarang.add(Pair.with(new Barang(2, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), new KategoriBarang(), "test3.com", true), 1));
//
//        FixedBill fixedBill = new FixedBill(listBarang);
//        Laporan laporan = new Laporan("src/test/resources/sample.pdf", fixedBill);
//        executorService.submit(() -> {
//            try {
//                laporan.save();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).get();
//    }
//
//    @Test
//    public void ListTest() throws ExecutionException, InterruptedException {
//        List<FixedBill> listBill = new ArrayList<>();
//        List<Pair<Barang, Integer>> listBarang = new ArrayList<>();
//
//        listBarang.add(Pair.with(new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(), "test.com", true), 2));
//        listBarang.add(Pair.with(new Barang(1, 100, "Pensil", new BigDecimal(2000), new BigDecimal(1500), new KategoriBarang(), "test2.com", true), 3));
//        listBarang.add(Pair.with(new Barang(2, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), new KategoriBarang(), "test3.com", true), 1));
//
//        listBill.add(new FixedBill(listBarang));
//
//        listBarang.clear();
//
//        listBarang.add(Pair.with(new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(), "test.com", true), 2));
//        listBarang.add(Pair.with(new Barang(1, 100, "Pensil", new BigDecimal(2000), new BigDecimal(1500), new KategoriBarang(), "test2.com", true), 3));
//        listBarang.add(Pair.with(new Barang(2, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), new KategoriBarang(), "test3.com", true), 1));
//
//        listBill.add(new FixedBill(listBarang));
//
//        listBarang.clear();
//
//        listBarang.add(Pair.with(new Barang(0, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(), "test.com", true), 2));
//        listBarang.add(Pair.with(new Barang(1, 100, "Pensil", new BigDecimal(2000), new BigDecimal(1500), new KategoriBarang(), "test2.com", true), 3));
//        listBarang.add(Pair.with(new Barang(2, 100, "Penghapus", new BigDecimal(500), new BigDecimal(400), new KategoriBarang(), "test3.com", true), 1));
//
//        listBill.add(new FixedBill(listBarang));
//
//        Laporan laporan = new Laporan("src/test/resources/sample_list.pdf", listBill);
//        executorService.submit(() -> {
//            try {
//                laporan.save();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).get();
//    }
//
////    @Test
////    public void NullTest() {
////        Laporan laporan = new Laporan("sample2.pdf", null);
////        laporan.save();
////    }
}
