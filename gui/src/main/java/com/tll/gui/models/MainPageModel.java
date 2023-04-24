package com.tll.gui.models;

import com.tll.gui.controllers.MainPageController;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainPageModel extends VBox {
    private MainPageController mainPageController;
    public MainPageModel(MainPageController mainPageController) {
        super();
        this.mainPageController = mainPageController;
        this.setPrefSize(600, 400);
        this.setAlignment(Pos.TOP_CENTER);
//        mainPageController.startClock();
        mainPageController.clockLabel.setFont(new Font(49.0));
        this.getChildren().addAll(mainPageController.getClockLabel(), mainPageController.getBottomLabel());
    }
}
