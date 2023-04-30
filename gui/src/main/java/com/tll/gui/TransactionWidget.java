package com.tll.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TransactionWidget extends HBox {
    private Label nameLabel;
    private Label idLabel;
    private Label dateLabel;

    public TransactionWidget(String name, String id, String date) {
        super();
        nameLabel = new Label("Name: " + name);
        idLabel = new Label("ID: " + id);
        dateLabel = new Label("Date: " + date);

        // Customize labels if needed

        setPadding(new Insets(5));
        getChildren().addAll(nameLabel, idLabel, dateLabel);
        HBox.setHgrow(this, Priority.ALWAYS);

        setMaxWidth(1.7976931348623157E308);
        setPrefWidth(2);

        // Add border to the TransactionWidget
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1));
        setBorder(new Border(borderStroke));
    }
}
