package com.tll.gui;

import com.tll.backend.pluginhandler.PluginContext;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.controllers.AppController;
import com.tll.gui.data.DataHandler;
import com.tll.gui.models.AppModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static String fileType;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(fileType);
        if (!fileType.equals(DataHandler.FileTypes.JSON.toString()) && !fileType.equals(DataHandler.FileTypes.XML.toString())
                && !fileType.equals(DataHandler.FileTypes.OBJ.toString()) && !fileType.equals(DataHandler.FileTypes.SQL.toString()) && !fileType.equals(DataHandler.FileTypes.SQL_ORM.toString())) {
            System.out.println("file type not recognized");
            return;
        }
        System.out.println(DataHandler.FileTypes.valueOf(fileType));
        BarangRepository barangRepository = new BarangRepository();
        FixedBillRepository fixedBillRepository = new FixedBillRepository();
        TemporaryBillRepository temporaryBillRepository = new TemporaryBillRepository();
//        FixedBill fixedBill =
//        Barang barang = new Barang(1,1,"a",new BigDecimal(1),new BigDecimal(1),new KategoriBarang(1,"x"), "a.jpg", true);
//        Barang barang2 = new Barang(3,3,"b",new BigDecimal(1),new BigDecimal(1),new KategoriBarang(1,"x"), "a.jpg", true);
//        Barang barang3 = new Barang(7,5,"c",new BigDecimal(1),new BigDecimal(1),new KategoriBarang(1,"x"), "a.jpg", true);
//
//        barangRepository.save(barang);
//        barangRepository.save(barang2);
//        barangRepository.save(barang3);

//        TemporaryBill tb = new TemporaryBill(temporaryBillRepository.getNextId());
//        tb.addToBill(barang2, 3);
//        temporaryBillRepository.save(tb);

        CustomerRepository customerRepository = new CustomerRepository();
//        Customer customer = new Customer(0);
//        Customer customer1 = new Customer(1);
//        Customer customer2 = new Customer(2);
//        Customer customer3 = new Customer(3);
//        customerRepository.save(customer);
//        customerRepository.save(customer1);
//        customerRepository.save(customer2);
//        customerRepository.save(customer3);

        MemberRepository memberRepository = new MemberRepository();
//        Member member = new Member(0, "paancoba", true, "asukon", "112", new ArrayList<>(), 10);
//        Member member1 = new Member(1, "paancba", true, "asuksson", "112", new ArrayList<>(), 10);
//        Member member2 = new Member(2, "paancobaa", true, "asusssskon", "112", new ArrayList<>(), 10);
//
//        memberRepository.save(member);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
        try (DataHandler handler = new DataHandler()) {
            String path = "../data/";
            handler.setSaveFolderPath(path);
            memberRepository = handler.load(MemberRepository.class, "member", DataHandler.FileTypes.valueOf(fileType));
            customerRepository = handler.load(CustomerRepository.class, "customer", DataHandler.FileTypes.valueOf(fileType));
            fixedBillRepository = handler.load(FixedBillRepository.class, "fixbill", DataHandler.FileTypes.valueOf(fileType));
            temporaryBillRepository = handler.load(TemporaryBillRepository.class, "tempbill", DataHandler.FileTypes.valueOf(fileType));
            barangRepository = handler.load(BarangRepository.class, "barang", DataHandler.FileTypes.valueOf(fileType));

        } catch (IOException exc) {
            System.out.println("gada");
        }

        PluginContext pluginContext = PluginContext.getInstance();

        stage.setTitle("Hello World!");
        AppController ac = new AppController(memberRepository, barangRepository, customerRepository, fixedBillRepository, temporaryBillRepository);
        VBox vbox = new AppModel(ac);
        pluginContext.addToContext("AppController", ac);

        Scene scene = new Scene(vbox, 1200, 640);
        stage.setTitle("Kasir-Kasiran Mantap!!!!!");


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length >= 1) {
            fileType = args[0];
        } else {
            fileType = "JSON";
        }
        Runtime.getRuntime().addShutdownHook(new Thread(AppController::shutdownPoolNow));
        launch();
    }
}