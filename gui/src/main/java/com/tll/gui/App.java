package com.tll.gui;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.controllers.AppController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.tll.gui.models.AppModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BarangRepository barangRepository = new BarangRepository();
        FixedBillRepository fixedBillRepository = new FixedBillRepository();
        TemporaryBillRepository temporaryBillRepository = new TemporaryBillRepository();
//        FixedBill fixedBill =
        Barang barang = new Barang(1,1,"a",new BigDecimal(1),new BigDecimal(1),new KategoriBarang(1,"x"), "a.jpg", true);
        Barang barang2 = new Barang(3,1,"a",new BigDecimal(1),new BigDecimal(1),new KategoriBarang(1,"x"), "a.jpg", true);
        Barang barang3 = new Barang(7,1,"a",new BigDecimal(1),new BigDecimal(1),new KategoriBarang(1,"x"), "a.jpg", true);

        barangRepository.save(barang);
        barangRepository.save(barang2);
        barangRepository.save(barang3);

        TemporaryBill tb = new TemporaryBill(temporaryBillRepository.getNextId());
        tb.addToBill(barang2, 3);
        temporaryBillRepository.save(tb);

        CustomerRepository customerRepository = new CustomerRepository();
        Customer customer = new Customer(0);
        Customer customer1 = new Customer(1);
        Customer customer2 = new Customer(2);
        Customer customer3 = new Customer(3);
        customerRepository.save(customer);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        MemberRepository memberRepository = new MemberRepository();
        Member member = new Member(0, "paancoba", true, "asukon", "112", new ArrayList<FixedBill>(), 10);
        Member member1 = new Member(1, "paancba", true, "asuksson", "112", new ArrayList<FixedBill>(), 10);
        Member member2 = new Member(2, "paancobaa", true, "asusssskon", "112", new ArrayList<FixedBill>(), 10);

        memberRepository.save(member);
        memberRepository.save(member1);
        memberRepository.save(member2);


        stage.setTitle("Hello World!");


        AppController ac = new AppController(barangRepository, temporaryBillRepository, fixedBillRepository, customerRepository, memberRepository);
        VBox vbox = new AppModel(ac);

        Scene scene = new Scene(vbox, 820, 640);
        stage.setTitle("Hello!");


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}