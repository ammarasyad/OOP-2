package com.tll.gui;

import com.tll.plugin.Plugin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class PluginWidget extends Pane {
    private static final double WIDTH = 150;
    private static final double HEIGHT = 100;
    private String pluginName;
    private boolean plugged;

    public PluginWidget(File file) {
        setPrefSize(WIDTH, HEIGHT);
        setMaxWidth(300);
        setPadding(new Insets(10, 10, 10, 10));

        HBox mainBox = new HBox();

        VBox pluginInfoBox = new VBox();

        this.pluginName = file.getName();
        Label nameLabel = new Label(pluginName);
        Label statusLabel = new Label("Plugged In");

        pluginInfoBox.getChildren().addAll(nameLabel, statusLabel);

        Button unplugButton = new Button("Unplug");

        mainBox.getChildren().addAll(pluginInfoBox, unplugButton);
        mainBox.setSpacing(20);
        mainBox.setPadding(new Insets(10));
        mainBox.setStyle("-fx-border-color: black;");
        mainBox.setAlignment(Pos.BASELINE_CENTER);

        getChildren().addAll(mainBox);
    }
}
