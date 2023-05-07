package com.tll.backend.datastore;

import com.tll.backend.datastore.loader.FileRepository;
import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.datastore.loader.ObjAdapter;
import com.tll.backend.datastore.loader.XmlAdapter;
import com.tll.backend.datastore.loader.hikari.HikariConfig;
import com.tll.backend.datastore.loader.sql.SqlAdapter;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import org.javatuples.Pair;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("unused")
public class FileRepositoryTest {
    // Test these one by one. Running them all will cause subsequent tests to fail due to closed SQL connection.

//    @Test
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
        try (HikariConfig connection = HikariConfig.INSTANCE) {
            SqlAdapter sqlAdapter = new SqlAdapter(connection);
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
                new Bill(1, List.of(Pair.with(barang, 2), Pair.with(barang, 3))),
                new Bill(2, List.of(Pair.with(barang, 1), Pair.with(barang, 4))),
                new Bill(3, List.of(Pair.with(barang, 5), Pair.with(barang, 6)))
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
        try (HikariConfig connection = HikariConfig.INSTANCE) {
            SqlAdapter sqlAdapter = new SqlAdapter(connection);
            FileRepository.save(barangList, sqlAdapter);
            FileRepository.save(billList, sqlAdapter);
            sql = FileRepository.load(Bill.class, sqlAdapter);
        }

        json.forEach(o -> assertEquals(o.getClass(), Bill.class, "JSON not equal"));
        xml.forEach(o -> assertEquals(o.getClass(), Bill.class, "XML not equal"));
        obj.forEach(o -> assertEquals(o.getClass(), Bill.class, "OBJ not equal"));
        sql.forEach(o -> assertEquals(o.getClass(), Bill.class, "SQL not equal"));
    }

//    @Test
    public void CustomerTest() throws IOException {
        Barang barang = new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true);
        FixedBill fixedBill = new FixedBill(1, 3, List.of(
                Pair.with(barang, 2), Pair.with(barang, 3),
                Pair.with(barang, 1), Pair.with(barang, 4),
                Pair.with(barang, 5), Pair.with(barang, 6)
        ));
        List<Customer> customers = List.of(
                new Customer(2, fixedBill),
                new Customer(3, fixedBill),
                new Customer(4, fixedBill)
        );

        JsonAdapter jsonAdapter = new JsonAdapter("src/test/resources/customer.json");
        XmlAdapter xmlAdapter = new XmlAdapter("src/test/resources/customer.xml");
        ObjAdapter objAdapter = new ObjAdapter("src/test/resources/customer.obj");

        FileRepository.save(customers, jsonAdapter);
        FileRepository.save(customers, xmlAdapter);
        FileRepository.save(customers, objAdapter);

        List<Customer> json = FileRepository.load(Customer.class, jsonAdapter);
        List<Customer> xml = FileRepository.load(Customer.class, xmlAdapter);
        List<Customer> obj = FileRepository.load(Customer.class, objAdapter);

        List<Customer> sql;

        try (var connection = HikariConfig.INSTANCE) {
            SqlAdapter sqlAdapter = new SqlAdapter(connection);
            FileRepository.save(customers, sqlAdapter);
            sql = FileRepository.load(Customer.class, sqlAdapter);
        }

        json.forEach(o -> assertEquals(o.getClass(), Customer.class, "JSON not equal"));
        xml.forEach(o -> assertEquals(o.getClass(), Customer.class, "XML not equal"));
        obj.forEach(o -> assertEquals(o.getClass(), Customer.class, "OBJ not equal"));
        sql.forEach(o -> assertEquals(o.getClass(), Customer.class, "SQL not equal"));
    }

//    @Test
    public void MemberTest() throws IOException {
        List<FixedBill> fixedBills_1 = List.of(
                new FixedBill(1, 1, List.of(
                        Pair.with(new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true), 2),
                        Pair.with(new Barang(2, 100, "Penggaris", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat2"), "test.com", true), 3)
                )),
                new FixedBill(2, 1, List.of(
                        Pair.with(new Barang(3, 100, "Pencil", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat3"), "test.com", true), 1),
                        Pair.with(new Barang(4, 100, "Macbook Pro M1", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat4"), "test.com", true), 4)
                )),
                new FixedBill(3, 1, List.of(
                        Pair.with(new Barang(5, 100, "iPhone 14 Pro Max", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat5"), "test.com", true), 5),
                        Pair.with(new Barang(6, 100, "Samsung Galaxy S23 Ultra", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat6"), "test.com", true), 6)
                ))
        );

        List<FixedBill> fixedBills_2 = List.of(
                new FixedBill(4, 2, List.of(
                        Pair.with(new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true), 2),
                        Pair.with(new Barang(2, 100, "Penggaris", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat2"), "test.com", true), 3)
                )),
                new FixedBill(5, 2, List.of(
                        Pair.with(new Barang(3, 100, "Pencil", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat3"), "test.com", true), 1),
                        Pair.with(new Barang(4, 100, "Macbook Pro M1", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat4"), "test.com", true), 4)
                )),
                new FixedBill(6, 2, List.of(
                        Pair.with(new Barang(5, 100, "iPhone 14 Pro Max", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat5"), "test.com", true), 5),
                        Pair.with(new Barang(6, 100, "Samsung Galaxy S23 Ultra", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat6"), "test.com", true), 6)
                ))
        );

        List<FixedBill> fixedBills_3 = List.of(
                new FixedBill(7, 3, List.of(
                        Pair.with(new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true), 2),
                        Pair.with(new Barang(2, 100, "Penggaris", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat2"), "test.com", true), 3)
                )),
                new FixedBill(8, 3, List.of(
                        Pair.with(new Barang(3, 100, "Pencil", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat3"), "test.com", true), 1),
                        Pair.with(new Barang(4, 100, "Macbook Pro M1", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat4"), "test.com", true), 4)
                )),
                new FixedBill(9, 3, List.of(
                        Pair.with(new Barang(5, 100, "iPhone 14 Pro Max", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat5"), "test.com", true), 5),
                        Pair.with(new Barang(6, 100, "Samsung Galaxy S23 Ultra", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat6"), "test.com", true), 6)
                ))
        );

        List<Barang> barangList_1 = fixedBills_1.stream().flatMap(o -> o.getCart().stream().map(Pair::getValue0)).toList();
        List<Barang> barangList_2 = fixedBills_2.stream().flatMap(o -> o.getCart().stream().map(Pair::getValue0)).toList();
        List<Barang> barangList_3 = fixedBills_3.stream().flatMap(o -> o.getCart().stream().map(Pair::getValue0)).toList();

        List<Member> members = List.of(
                Member.builder().id(1).type("type1").activeStatus(true).name("test1").bills(fixedBills_1).phone("081234567890").point(1L).build(),
                Member.builder().id(2).type("type2").activeStatus(true).name("test2").bills(fixedBills_2).phone("081234567890").point(2L).build(),
                Member.builder().id(3).type("type3").activeStatus(true).name("test3").bills(fixedBills_3).phone("081234567890").point(3L).build()
        );
        JsonAdapter jsonAdapter = new JsonAdapter("src/test/resources/member.json");
        XmlAdapter xmlAdapter = new XmlAdapter("src/test/resources/member.xml");
        ObjAdapter objAdapter = new ObjAdapter("src/test/resources/member.obj");

        FileRepository.save(members, jsonAdapter);
        FileRepository.save(members, xmlAdapter);
        FileRepository.save(members, objAdapter);

        List<Member> json = FileRepository.load(Member.class, jsonAdapter);
        List<Member> xml = FileRepository.load(Member.class, xmlAdapter);
        List<Member> obj = FileRepository.load(Member.class, objAdapter);

        List<Member> sql;
        try (HikariConfig connection = HikariConfig.INSTANCE) {
            SqlAdapter sqlAdapter = new SqlAdapter(connection);
//            FileRepository.save(members, sqlAdapter);
            sql = FileRepository.load(Member.class, sqlAdapter);
        }

        json.forEach(o -> assertEquals(o.getClass(), Member.class, "JSON not equal"));
        xml.forEach(o -> assertEquals(o.getClass(), Member.class, "XML not equal"));
        obj.forEach(o -> assertEquals(o.getClass(), Member.class, "OBJ not equal"));
        sql.forEach(o -> assertEquals(o.getClass(), Member.class, "SQL not equal"));
    }

//    @Test
    public void TemporaryBillTest() throws IOException {
        Barang barang = new Barang(1, 100, "Buku", new BigDecimal(1000), new BigDecimal(800), new KategoriBarang(0, "kat1"), "test.com", true);
        List<Barang> barangList = List.of(barang);

        List<TemporaryBill> billList = List.of(
                new TemporaryBill(1),
                new TemporaryBill(2),
                new TemporaryBill(3)
        );

        AtomicInteger test = new AtomicInteger(1);
        billList.forEach(bill -> bill.addToBill(barang, test.getAndIncrement()));

        JsonAdapter jsonAdapter = new JsonAdapter("src/test/resources/temp_bill.json");
        XmlAdapter xmlAdapter = new XmlAdapter("src/test/resources/temp_bill.xml");
        ObjAdapter objAdapter = new ObjAdapter("src/test/resources/temp_bill.obj");

        FileRepository.save(billList, jsonAdapter);
        FileRepository.save(billList, xmlAdapter);
        FileRepository.save(billList, objAdapter);

        List<TemporaryBill> json = FileRepository.load(TemporaryBill.class, jsonAdapter);
        List<TemporaryBill> xml = FileRepository.load(TemporaryBill.class, xmlAdapter);
        List<TemporaryBill> obj = FileRepository.load(TemporaryBill.class, objAdapter);
    }
}
