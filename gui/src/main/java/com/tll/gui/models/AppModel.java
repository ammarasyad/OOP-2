package com.tll.gui.models;

import com.tll.gui.controllers.AppController;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AppModel extends VBox {
    private AppController appController;
    public AppModel(AppController appController){
        super();
        this.appController = appController;

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(appController.getPages());

        // Create an HBox to hold the tab pane and make it stretch to fill the window
        HBox hbox = new HBox();
        hbox.getChildren().add(appController.getTabPane());
        hbox.setHgrow(appController.getTabPane(), javafx.scene.layout.Priority.ALWAYS);

        // to hold the menu bar and the HBox
        this.getChildren().addAll(menuBar, hbox);
        VBox.setVgrow(hbox, Priority.ALWAYS);

    }
}
