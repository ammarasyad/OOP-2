package com.tll.gui;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.gui.controllers.AppController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.tll.gui.models.AppModel;

import java.io.IOException;
import java.math.BigDecimal;

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

        TemporaryBill tb = new TemporaryBill(0,0);
        tb.addToBill(barang2, 3);
        temporaryBillRepository.save(tb);

        stage.setTitle("Hello World!");

        AppController ac = new AppController(barangRepository, temporaryBillRepository);
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