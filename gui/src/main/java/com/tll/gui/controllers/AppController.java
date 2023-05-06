package com.tll.gui.controllers;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.ClosableTab;
import com.tll.gui.factory.PageFactory;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.Getter;
import com.tll.gui.models.UpdatePageControl;

@Getter
public class AppController {
    //Pages menu
    private Menu pages;
    // items :
    private MenuItem mainPage;
    private MenuItem registerPage;
    private MenuItem updatePage;
    private MenuItem historyPage;
    private MenuItem kasirPage;
    private MenuItem settingPage;
    private MenuItem InsertPage;
    private TabPane tabPane;
    private MainPageModel mainPageModel;
    private RegisterPageModel registerPageModel;
    private UpdatePageModel updatePageModel;
    private BarangRepository barangRepository;
    private TemporaryBillRepository temporaryBillRepository;
    private FixedBillRepository fixedBillRepository;
    private CustomerRepository customerRepository;
    private MemberRepository memberRepository;

    private static final String OPEN_PAGE = "Open Page";
    private static final String MAIN_PAGE = "Main";
    private static final String REGISTER_PAGE = "Register";
    private static final String UPDATE_PAGE = "Update";
    private static final String HISTORY_PAGE = "History";
    private static final String KASIR_PAGE = "Kasir";
    private static final String INSERT_PAGE = "Insert";
    private static final String SETTING_PAGE = "Setting";


    public AppController(BarangRepository barangRepository, TemporaryBillRepository temporaryBillRepository,
                         FixedBillRepository fixedBillRepository,
                         CustomerRepository customerRepository, MemberRepository memberRepository) {
        this.barangRepository = barangRepository;
        this.temporaryBillRepository = temporaryBillRepository;
        this.fixedBillRepository = fixedBillRepository;
        this.customerRepository = customerRepository;
        this.memberRepository = memberRepository;
        mainPageModel = new MainPageModel();
        registerPageModel = new RegisterPageModel(customerRepository);

        pages = new Menu(OPEN_PAGE);
        mainPage = new MenuItem(MAIN_PAGE);
        registerPage = new MenuItem(REGISTER_PAGE);
        updatePage = new MenuItem(UPDATE_PAGE);
        historyPage = new MenuItem(HISTORY_PAGE);
        kasirPage = new MenuItem(KASIR_PAGE);
        InsertPage = new MenuItem(INSERT_PAGE);
        settingPage = new MenuItem(SETTING_PAGE);
        pages.getItems().addAll(mainPage, registerPage, updatePage, historyPage, kasirPage, settingPage, InsertPage);
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        mainPage.setOnAction((event -> addMainPage()));
        registerPage.setOnAction(event -> addRegisterPage());
        updatePage.setOnAction(event -> addUpdatePage());
        historyPage.setOnAction(event -> addHistoryPage());
        kasirPage.setOnAction(event -> {
            TemporaryBill temporaryBill = new TemporaryBill(0);
            temporaryBillRepository.save(temporaryBill);
            addKasirPage(new KasirPageModel(temporaryBill));
        });
        settingPage.setOnAction(event -> addSetting());
        InsertPage.setOnAction(event ->addInsertPage());

        loadKasirPages();
    }
    private void loadKasirPages(){
        for(TemporaryBill temporaryBill : temporaryBillRepository.findAll()){
            addKasirPage(new KasirPageModel(temporaryBill));
        }
    }
    private void addMainPage() {
        ClosableTab tab = new ClosableTab(MAIN_PAGE);
        tab.setContent(PageFactory.getMainPage(mainPageModel));
        tabPane.getTabs().add(tab);
    }
    private void addRegisterPage() {
        ClosableTab tab = new ClosableTab(REGISTER_PAGE);
        tab.setContent(PageFactory.getRegisterPage(registerPageModel));
        tabPane.getTabs().add(tab);
    }

    private void addUpdatePage() {
        updatePageModel = new UpdatePageModel(memberRepository);
        ClosableTab tab = new ClosableTab(REGISTER_PAGE);
        tab.setContent(PageFactory.getUpdatePage(updatePageModel));
        tabPane.getTabs().add(tab);
    }

    private void addHistoryPage() {
        ClosableTab tab = new ClosableTab(HISTORY_PAGE);
        tab.setContent(PageFactory.getHistoryPage());
        tabPane.getTabs().add(tab);
    }

    private void addKasirPage(KasirPageModel kasirPageModel) {
//        temporaryBillRepository.save(new TemporaryBill(0, 0));
//        var tempBill = temporaryBillRepository.findById(0).orElseThrow(() -> new RuntimeException());
//        KasirPageModel kasirPageModel = new KasirPageModel(tempBill);
        ClosableTab tab = new ClosableTab(KASIR_PAGE);
        tab.setContent(PageFactory.getKasirPage(temporaryBillRepository, barangRepository, fixedBillRepository, customerRepository, kasirPageModel));
        tabPane.getTabs().add(tab);
    }

    private void addSetting() {
        ClosableTab tab = new ClosableTab(SETTING_PAGE);
        tab.setContent(PageFactory.getSetting());
        tabPane.getTabs().add(tab);
    }

    private void addInsertPage(){
        ClosableTab tab = new ClosableTab(INSERT_PAGE);
        tab.setContent(PageFactory.getInsertBarangPage(barangRepository));
        tabPane.getTabs().add(tab);
    }


}
