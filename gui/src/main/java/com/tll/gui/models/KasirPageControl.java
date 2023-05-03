package com.tll.gui.models;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.gui.DisplayWidget;
import com.tll.gui.ProductWidget;
import com.tll.gui.controllers.KasirPageModel;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.javatuples.Pair;

import java.util.concurrent.Flow;

public class KasirPageControl {
    private TemporaryBillRepository temporaryBillRepository;
    private BarangRepository barangRepository;
    private KasirPageModel kasirPageModel;

    public KasirPageControl(TemporaryBillRepository temporaryBillRepository,
                            BarangRepository barangRepository,
                            KasirPageModel kasirPageModel){
        this.barangRepository = barangRepository;
        this.temporaryBillRepository = temporaryBillRepository;
        this.kasirPageModel = kasirPageModel;

        FlowPane productList = kasirPageModel.getProductsList();
        VBox selectedItem = kasirPageModel.getSelectedItem();
        for(Barang barang : barangRepository.findAll()){
            DisplayWidget displayWidget = new DisplayWidget(barang);
            displayWidget.setOnMouseClicked(event -> {
                ProductWidget productWidget = new ProductWidget(displayWidget);
                productList.getChildren().addAll(productWidget);
            });

            productList.getChildren().addAll(displayWidget);
        }

        for(Pair<Barang, Integer> item : kasirPageModel.getTemporaryBill().getCart()){
            addToSelected(item);
        }


    }

    private void addToSelected(Pair<Barang, Integer> item){
        FlowPane productList = kasirPageModel.getProductsList();
        for(Node node : productList.getChildren()){
            if(node instanceof DisplayWidget){
                DisplayWidget displayWidget = (DisplayWidget) node;
                if(displayWidget.getBarang().equals(item.getValue0())){
                    for(int i = 0; i < item.getValue1(); i++){
                        displayWidget.getOnMouseClicked().handle(null);
                    }
                }
            }
        }
    }

}
