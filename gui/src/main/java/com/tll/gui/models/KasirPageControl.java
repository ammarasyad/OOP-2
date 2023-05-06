package com.tll.gui.models;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.DisplayWidget;
import com.tll.gui.ProductWidget;
import com.tll.gui.controllers.KasirPageModel;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.javatuples.Pair;

import java.util.concurrent.Flow;

public class KasirPageControl {
    private BarangRepository barangRepository;
    private KasirPageModel kasirPageModel;
    private TemporaryBillRepository temporaryBillRepository;
    private FixedBillRepository fixedBillRepository;
    private CustomerRepository customerRepository;
    private MemberRepository memberRepository;

    public KasirPageControl(TemporaryBillRepository temporaryBillRepository,
                            FixedBillRepository fixedBillRepository,
                            BarangRepository barangRepository,
                            CustomerRepository customerRepository,
                            MemberRepository memberRepository,
                            KasirPageModel kasirPageModel){
        this.temporaryBillRepository = temporaryBillRepository;
        this.fixedBillRepository = fixedBillRepository;
        this.barangRepository = barangRepository;
        this.customerRepository = customerRepository;
        this.memberRepository = memberRepository;
        this.kasirPageModel = kasirPageModel;

        refreshProductList();

        for(Pair<Barang, Integer> item : kasirPageModel.getTemporaryBill().getCart()){
            addToSelected(item);
        }


    }

    private void refreshProductList(){
        FlowPane productList = kasirPageModel.getProductsList();
        VBox selectedItem = kasirPageModel.getSelectedItem();
        for(Barang barang : barangRepository.findAll()){
            DisplayWidget displayWidget = new DisplayWidget(barang);
            displayWidget.setOnMouseClicked(event -> {
                ProductWidget productWidget = new ProductWidget(displayWidget);
                selectedItem.getChildren().addAll(productWidget);
            });

            productList.getChildren().addAll(displayWidget);
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

    public void saveTemporaryBill(){
        kasirPageModel.getTemporaryBill().emptyBill();
        for(Node node : kasirPageModel.getSelectedItem().getChildren()){
            if(node instanceof ProductWidget){
                ProductWidget productWidget = (ProductWidget) node;
                kasirPageModel.getTemporaryBill().addToBill(productWidget.getBarang(), productWidget.getQuantity());
            }
        }
    }

    public void checkOut(){
        Customer customer = new Customer(customerRepository.getLargestId()+1);
        FixedBill fixedBill = new FixedBill(fixedBillRepository.getNextId(),customer.getId(), kasirPageModel.getTemporaryBill().getCart());
        temporaryBillRepository.delete(kasirPageModel.getTemporaryBill());
        fixedBillRepository.save(fixedBill);
//        memberRepository.memberPay()
    }

}

