package com.tll.gui.controllers;

import com.tll.gui.ClosableTab;
import javafx.scene.control.*;
import lombok.Getter;
import com.tll.gui.models.MainPageModel;
import com.tll.gui.models.RegisterPageModel;
import com.tll.gui.models.UpdatePageModel;

@Getter
public class AppController {
    //Pages menu
    private Menu pages;
    // items :
    private MenuItem mainPage;
    private MenuItem registerPage;
    private MenuItem updatePage;
    private TabPane tabPane;

    public AppController() {
        pages = new Menu("Open Page");
        mainPage = new MenuItem("Main Page");
        registerPage = new MenuItem("Register Page");
        updatePage = new MenuItem("Update Page");
        pages.getItems().addAll(mainPage, registerPage, updatePage);
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        mainPage.setOnAction((event -> addMainPage()));
        registerPage.setOnAction(event -> addRegisterPage());
        updatePage.setOnAction(event -> addUpdatePage());
    }
    private void addMainPage() {
        MainPageController mc = new MainPageController();
        MainPageModel mainPage = new MainPageModel(mc);
        mc.startClock();
        ClosableTab tab = new ClosableTab("Main Page");
        tab.setContent(mainPage);
        tabPane.getTabs().add(tab);
    }
    private void addRegisterPage() {
        RegisterPageController rpc = new RegisterPageController();
        RegisterPageModel registerPage = new RegisterPageModel(rpc);
        ClosableTab tab = new ClosableTab("Register Page");
        tab.setContent(registerPage);
        tabPane.getTabs().add(tab);
    }

    private void addUpdatePage() {
        UpdatePageController upc = new UpdatePageController();
        UpdatePageModel registerPage = new UpdatePageModel(upc);
        ClosableTab tab = new ClosableTab("Update Page");
        tab.setContent(registerPage);
        tabPane.getTabs().add(tab);
    }
}
