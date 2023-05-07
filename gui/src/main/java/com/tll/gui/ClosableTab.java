package com.tll.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class ClosableTab extends Tab {
    private Label closeLabel;
    public ClosableTab(String name){
        super();
        closeLabel = new Label("\u2716");
        closeLabel.setVisible(false);
        HBox tabHeader = new HBox(new Label(name), closeLabel);

        setGraphic(tabHeader);

        tabHeader.setOnMouseEntered(e -> {
            closeLabel.setStyle("-fx-background-color: #ccc; -fx-background-radius: 50%");
            // Set the background of the label to the gradient
//            closeLabel.setBackground(background);
            closeLabel.setVisible(true);
        });
        tabHeader.setOnMouseExited(e -> {
            closeLabel.setStyle("-fx-background-color: transparent;");

            closeLabel.setVisible(false);
        });
        closeLabel.setOnMouseClicked(e -> {
            getTabPane().getTabs().remove(this);
            e.consume();
        });
    }

    public ClosableTab(){
        this("");
    }
}
