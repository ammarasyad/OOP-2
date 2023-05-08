package com.tll.gui.models;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.backend.repository.impl.user.helper.CustomerMemberHelper;
import com.tll.gui.DisplayWidget;
import com.tll.gui.ProductWidget;
import com.tll.gui.controllers.KasirPageModel;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.util.Optional;
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

        kasirPageModel.getBillStatus().setText("Bill ID: "+kasirPageModel.getTemporaryBill().getId());

        refreshProductList();

        kasirPageModel.getMembers().getSelectionModel().select(0);
    }

    public void refreshProductList(){
        FlowPane productList = kasirPageModel.getProductsList();
        VBox selectedItem = kasirPageModel.getSelectedItem();
        productList.getChildren().clear();
        selectedItem.getChildren().clear();
        for(Barang barang : barangRepository.findAll()){
            DisplayWidget displayWidget = new DisplayWidget(barang);
            displayWidget.setOnMouseClicked(event -> {
                ProductWidget productWidget = new ProductWidget(displayWidget);
                selectedItem.getChildren().addAll(productWidget);
            });

            productList.getChildren().addAll(displayWidget);
//            displayWidget.setManaged();
//            displayWidget.setVisible();
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
        saveTemporaryBill();
        System.out.println(fixedBillRepository.getNextId());
        CustomerMemberHelper customerMemberHelper = new CustomerMemberHelper(customerRepository, memberRepository);
        if(kasirPageModel.getUseMember().isSelected()){
            Optional<Member> optMember = memberRepository.findById(kasirPageModel.getMembers().getValue().getId());
            if (optMember.isEmpty()) {
                throw new RuntimeException("unexpected error happened!");
            }
            var member = optMember.get();
            FixedBill fixedBill = kasirPageModel.getTemporaryBill().convertToFixedBill(fixedBillRepository.getNextId(),
                    member.getId());
            if (member.getType().equals("Vip") && member.isActiveStatus()) {
                fixedBill.AddVIPDiscount();
            }
            memberRepository.memberPay(kasirPageModel.getMembers().getValue().getId(), fixedBill);
            fixedBillRepository.save(fixedBill);
            temporaryBillRepository.delete(kasirPageModel.getTemporaryBill());
            System.out.println(fixedBill.getUserId());
            System.out.println(fixedBillRepository.getNextId());

        } else {
            Customer customer = new Customer(customerMemberHelper.getLargestId()+1);
            customerRepository.save(customer);
            FixedBill fixedBill = kasirPageModel.getTemporaryBill().convertToFixedBill(fixedBillRepository.getNextId(),customer.getId());
            temporaryBillRepository.delete(kasirPageModel.getTemporaryBill());
            fixedBillRepository.save(fixedBill);
            System.out.println(fixedBill.getUserId());
            System.out.println(fixedBillRepository.getNextId());
        }

        kasirPageModel.getBillStatus().setText("Checked Out.");




//        memberRepository.memberPay()
    }

    public void searchByName(String input){
        for (Node node : kasirPageModel.getProductsList().getChildren()) {
            if(node instanceof DisplayWidget) {
                DisplayWidget displayWidget = (DisplayWidget) node;
                if (!(displayWidget.getBarang().getNama().toLowerCase().startsWith(input.toLowerCase())
                        || displayWidget.getBarang().getHarga().toString().toLowerCase().startsWith(input.toLowerCase())
                        || displayWidget.getKategori().toLowerCase().startsWith(input.toLowerCase()))) {
                    displayWidget.setVisible(false);
                    displayWidget.setManaged(false);
                } else {
                    displayWidget.setVisible(true);
                    displayWidget.setManaged(true);
                }
            }
        }
    }

}

