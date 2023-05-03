package com.tll.gui;

import com.tll.backend.model.barang.Barang;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class DisplayWidget extends Pane {

    private static final double WIDTH = 150;
    private static final double HEIGHT = 100;
    private static final double IMAGE_SIZE = 50;

    private String name;
    private String productId;
    private String price;
    private Barang barang;

    public DisplayWidget(Barang barang) {
        setPrefSize(WIDTH, HEIGHT);
        this.barang = barang;
        this.name = barang.getNama();
        this.productId = barang.getId().toString();
        this.price = barang.getHarga().toString();
        Label nameLabel = new Label("Name: " + name);
        Label idLabel = new Label("ID: " + productId);
        Label priceLabel = new Label("Price: " + price);

        // Create and configure the rectangle shape
        Rectangle rectangle = new Rectangle(WIDTH, HEIGHT, Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1.5);

        // Create and configure the image view
        Image a = new Image(getClass().getResourceAsStream("a.jpg"));
        ImageView imageView = new ImageView(a);
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);
        imageView.setLayoutX(10);
        imageView.setLayoutY((HEIGHT - IMAGE_SIZE) / 2);

        // Create and configure the labels
        nameLabel = createLabel(name, 70);
        nameLabel.setLayoutX(IMAGE_SIZE + 20);
        nameLabel.setLayoutY(20);

        idLabel = createLabel("ID: " + productId, 60);
        idLabel.setLayoutX(IMAGE_SIZE + 20);
        idLabel.setLayoutY(40);

        priceLabel = createLabel("Price: $" + price, 60);
        priceLabel.setLayoutX(IMAGE_SIZE + 20);
        priceLabel.setLayoutY(60);

        // Add all elements to the pane
        getChildren().addAll(rectangle, imageView, nameLabel, idLabel, priceLabel);

    }

    private Label createLabel(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setWrapText(true);
        return label;
    }
}

