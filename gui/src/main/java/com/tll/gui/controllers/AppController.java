package com.tll.gui.controllers;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.ClosableTab;
import com.tll.gui.factory.NodeFactory;
import com.tll.gui.factory.PageFactory;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public class AppController {
    //Pages menu
    private MenuItem refresh;
    private Menu pages;
    private Menu util;
    // items :
    private final MenuItem mainPage;
    private final MenuItem registerPage;
    private final MenuItem updatePage;
    private final MenuItem historyPage;
    private final MenuItem kasirPage;
    private final MenuItem settingPage;
    private final MenuItem InsertPage;
    private final TabPane tabPane;
    private final MainPageModel mainPageModel;
    private RegisterPageModel registerPageModel;
    private UpdatePageModel updatePageModel;
    private final BarangRepository barangRepository;
    private final TemporaryBillRepository temporaryBillRepository;
    private final FixedBillRepository fixedBillRepository;
    private final CustomerRepository customerRepository;
    private final MemberRepository memberRepository;
    private final List<File> fileList;
    private final List<Node> pluginNodes;
    private final List<NodeFactory> kasirAdditions;

    private static final String OPEN_PAGE = "Open Page";
    private static final String MAIN_PAGE = "Main";
    private static final String REGISTER_PAGE = "Register";
    private static final String UPDATE_PAGE = "Update";
    private static final String HISTORY_PAGE = "History";
    private static final String KASIR_PAGE = "Kasir";
    private static final String INSERT_PAGE = "Insert";
    private static final String SETTING_PAGE = "Setting";

    // Thread pool for running background tasks (for plugins)
    private static final ScheduledExecutorService commonScheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    });

    public AppController(MemberRepository memberRepository, BarangRepository barangRepository, CustomerRepository customerRepository, FixedBillRepository fixedBillRepository, TemporaryBillRepository temporaryBillRepository) {
        this.barangRepository = barangRepository;
        this.temporaryBillRepository = temporaryBillRepository;
        this.fixedBillRepository = fixedBillRepository;
        this.customerRepository = customerRepository;
        this.memberRepository = memberRepository;
        this.fileList = new ArrayList<>();
        this.pluginNodes = new ArrayList<>();
        this.kasirAdditions = new ArrayList<>();
        mainPageModel = new MainPageModel();
        registerPageModel = new RegisterPageModel(customerRepository, memberRepository);

        pages = new Menu(OPEN_PAGE);
        util = new Menu("↻");
        refresh = new MenuItem("Refresh Pages");
        util.getItems().add(refresh);
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
            TemporaryBill temporaryBill = new TemporaryBill(temporaryBillRepository.getNextId());
            temporaryBillRepository.save(temporaryBill);
            addKasirPage(new KasirPageModel(temporaryBill, memberRepository));
        });
        settingPage.setOnAction(event -> {
            boolean existSetting = false;
            for (Tab tab : tabPane.getTabs()) {
                System.out.println(((ClosableTab) tab).getName());
                if (((ClosableTab) tab).getName().equals(SETTING_PAGE)) {
                    existSetting = true;
                }
            }
            if (!existSetting) {
                addSetting(new SettingPageModel());
            }
        });
        InsertPage.setOnAction(event -> addInsertPage());
        refresh.setOnAction(e -> loadKasirPages());
    }

    private void loadKasirPages() {
        for (TemporaryBill temporaryBill : temporaryBillRepository.findAll()) {
            addKasirPage(new KasirPageModel(temporaryBill, memberRepository));
        }
    }

    private void addMainPage() {
        ClosableTab tab = new ClosableTab(MAIN_PAGE);
        tab.setContent(PageFactory.getMainPage(mainPageModel));
        tabPane.getTabs().add(tab);
    }

    private void addRegisterPage() {
        registerPageModel = new RegisterPageModel(customerRepository, memberRepository);
        ClosableTab tab = new ClosableTab(REGISTER_PAGE);
        tab.setContent(PageFactory.getRegisterPage(registerPageModel));
        tabPane.getTabs().add(tab);
    }

    private void addUpdatePage() {
        updatePageModel = new UpdatePageModel(memberRepository);
        ClosableTab tab = new ClosableTab(UPDATE_PAGE);
        tab.setContent(PageFactory.getUpdatePage(updatePageModel));
        tabPane.getTabs().add(tab);
    }

    private void addHistoryPage() {
        HistoryPageModel historyPageModel = new HistoryPageModel();
        ClosableTab tab = new ClosableTab(HISTORY_PAGE);
        tab.setContent(PageFactory.getHistoryPage(fixedBillRepository, historyPageModel));
        tabPane.getTabs().add(tab);
    }

    private void addKasirPage(KasirPageModel kasirPageModel) {
//        temporaryBillRepository.save(new TemporaryBill(0, 0));
//        var tempBill = temporaryBillRepository.findById(0).orElseThrow(() -> new RuntimeException());
//        KasirPageModel kasirPageModel = new KasirPageModel(tempBill);
        ClosableTab tab = new ClosableTab(KASIR_PAGE);
        tab.setContent(PageFactory.getKasirPage(temporaryBillRepository, barangRepository, fixedBillRepository, customerRepository,
                memberRepository, kasirPageModel, kasirAdditions));
        tabPane.getTabs().add(tab);
    }

    private void addSetting(SettingPageModel settingPageModel) {
        ClosableTab tab = new ClosableTab(SETTING_PAGE);
        tab.setContent(PageFactory.getSetting(fileList, settingPageModel, pluginNodes, barangRepository,
                temporaryBillRepository, fixedBillRepository, customerRepository, memberRepository));
        tabPane.getTabs().add(tab);
    }

    private void addInsertPage() {
        ClosableTab tab = new ClosableTab(INSERT_PAGE);
        tab.setContent(PageFactory.getInsertBarangPage(barangRepository));
        tabPane.getTabs().add(tab);
    }

    public static ScheduledExecutorService getCommonScheduledThreadPool() {
        return commonScheduledThreadPool;
    }

    public static void shutdownPoolNow() {
        commonScheduledThreadPool.shutdownNow();
    }
}
