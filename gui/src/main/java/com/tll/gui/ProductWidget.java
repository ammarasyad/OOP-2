package com.tll.gui;

import com.tll.backend.model.barang.Barang;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class ProductWidget extends HBox {
    private String name;
    private String productId;
    private String price;
    private Barang barang;
    private int quantity;
    @Getter(value = AccessLevel.NONE)
    private EventHandler<MouseEvent> previousEventHandler;

    public ProductWidget(DisplayWidget displayWidget) {
        super();
        previousEventHandler = (EventHandler<MouseEvent>) displayWidget.getOnMouseClicked();
        this.name = displayWidget.getName();
        this.productId = displayWidget.getProductId();
        this.price = displayWidget.getPrice();
        this.barang = displayWidget.getBarang();
        this.quantity = 1;
        Label nameLabel = new Label("Name: " + name);
        nameLabel.setPadding(new Insets(5, 0, 0, 5));
        Label idLabel = new Label("ID: " + productId);
        idLabel.setPadding(new Insets(5, 0, 0, 5));
        Label priceLabel = new Label("Price: " + price);
        priceLabel.setPadding(new Insets(5, 0, 0, 5));
        Label quantityLabel = new Label("1");
        Button plusButton = new Button("+");
        Button minusButton = new Button("-");
        // button + - box
        HBox buttonBox = new HBox(10, minusButton, quantityLabel, plusButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Customize labels if needed
        setPadding(new Insets(5));
        getChildren().addAll(nameLabel, idLabel, priceLabel, buttonBox);

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
            quantity++;
            quantityLabel.setText(Integer.toString(quantity));


        });

        // kurang item, hapus jika quantity = 0
        minusButton.setOnAction(e -> {
            if (quantity > 0) {
                quantity--;
                quantityLabel.setText(Integer.toString(quantity));
            }
            if (quantity == 0) {
                ((Pane) getParent()).getChildren().remove(this);
                displayWidget.setOnMouseClicked(previousEventHandler);
            }
        });

        displayWidget.setOnMouseClicked(event -> {
            plusButton.fire();
        });
    }
}
