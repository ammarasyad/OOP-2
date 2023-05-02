package com.tll.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ProductWidget extends HBox {
    private Label nameLabel;
    private Label idLabel;
    private Label priceLabel;

    private Label quantityLabel;

    private Button plusButton;
    private Button minusButton;

    private HBox buttonBox;


    public ProductWidget(String name, String id, String price) {
        super();
        nameLabel = new Label("Name: " + name);
        idLabel = new Label("ID: " + id);
        priceLabel = new Label("Price: " + price);
        quantityLabel = new Label("1");
        plusButton = new Button("+");
        minusButton = new Button("-");

        // button + - box
        buttonBox = new HBox(10, minusButton, quantityLabel, plusButton);
       buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Customize labels if needed
        setPadding(new Insets(5));
        getChildren().addAll(nameLabel, idLabel, priceLabel);

        HBox.setHgrow(buttonBox, Priority.ALWAYS);

        setMaxWidth(1.7976931348623157E308);
        setPrefWidth(2);

        // Add border to the TransactionWidget
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1));
        setBorder(new Border(borderStroke));

        // nambah item
        plusButton.setOnAction(e -> {
           int quantity = Integer.parseInt(quantityLabel.getText());
            quantity++;
            quantityLabel.setText(Integer.toString(quantity));


        });

        // kurang item, hapus jika quantity = 0
        minusButton.setOnAction(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) {
                quantity--;
                quantityLabel.setText(Integer.toString(quantity));
            }
            if (quantity == 0) {
                ((Pane) getParent()).getChildren().remove(this);
            }
        });
    }

    // buttonbox supaya cuma ke bagian kanan
    public HBox getButtonBox() {
        return buttonBox;
    }
}
