package com.tll.gui.controllers;

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
    private TabPane tabPane;
    private MainPageModel mainPageModel;
    private RegisterPageModel registerPageModel;
    private UpdatePageModel updatePageModel;

    private VBox sidebar;

    public AppController() {
        mainPageModel = new MainPageModel();
        registerPageModel = new RegisterPageModel();
        updatePageModel = new UpdatePageModel();

        pages = new Menu("Open Page");
        mainPage = new MenuItem("Main Page");
        registerPage = new MenuItem("Register Page");
        updatePage = new MenuItem("Update Page");
        historyPage = new MenuItem("History Page");
        kasirPage = new MenuItem("Kasir Page");
        pages.getItems().addAll(mainPage, registerPage, updatePage, historyPage, kasirPage);
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        mainPage.setOnAction((event -> addMainPage()));
        registerPage.setOnAction(event -> addRegisterPage());
        updatePage.setOnAction(event -> addUpdatePage());
        historyPage.setOnAction(event -> addHistoryPage());
        kasirPage.setOnAction(event -> addKasirPage());


    }
    private void addMainPage() {
        ClosableTab tab = new ClosableTab("Main Page");
        tab.setContent(PageFactory.getMainPage(mainPageModel));
        tabPane.getTabs().add(tab);
    }
    private void addRegisterPage() {
        ClosableTab tab = new ClosableTab("Register Page");
        tab.setContent(PageFactory.getRegisterPage(registerPageModel));
        tabPane.getTabs().add(tab);
    }

    private void addUpdatePage() {
        ClosableTab tab = new ClosableTab("Update Page");
        tab.setContent(PageFactory.getUpdatePage(updatePageModel));
        tabPane.getTabs().add(tab);
    }

    private void addHistoryPage() {
        ClosableTab tab = new ClosableTab("History Page");
        tab.setContent(PageFactory.getHistoryPage());
        tabPane.getTabs().add(tab);
    }

    private void addKasirPage() {
        ClosableTab tab = new ClosableTab("History Page");
        tab.setContent(PageFactory.getKasirPage());
        tabPane.getTabs().add(tab);
    }
}
