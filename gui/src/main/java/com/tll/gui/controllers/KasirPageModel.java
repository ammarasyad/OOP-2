package com.tll.gui.controllers;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class KasirPageModel {
    private TemporaryBill temporaryBill;
    private FlowPane productsList;
    private VBox selectedItem;

    public KasirPageModel(TemporaryBill temporaryBill){
        this.temporaryBill = temporaryBill;
        productsList = new FlowPane();
        selectedItem = new VBox();
    }
}
