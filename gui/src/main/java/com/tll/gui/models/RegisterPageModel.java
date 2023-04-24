package com.tll.gui.models;

import com.tll.gui.controllers.RegisterPageController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RegisterPageModel extends VBox {
    RegisterPageController registerPageController;
    public RegisterPageModel(RegisterPageController registerPageController){
        super();
        this.registerPageController = registerPageController;
        this.setPrefSize(600, 400);
        this.setAlignment(Pos.TOP_LEFT);

        Label label = new Label("Register");
        label.setFont(new Font(38.0));
        VBox.setMargin(label, new Insets(0, 0, 0, 10)); // set left margin of label

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #ddd;");

        VBox leftVbox = new VBox();
        leftVbox.setPrefSize(100, 200);
        leftVbox.setSpacing(10);
        leftVbox.setPadding(new Insets(10));

        Label nameLabel = new Label("Nama :");
        TextField nameTextField = registerPageController.getNameTextField();
        Label phoneLabel = new Label("Nomor Telepon :");
        TextField phoneTextField = registerPageController.getPhoneTextField();

        leftVbox.getChildren().addAll(nameLabel, nameTextField, phoneLabel, phoneTextField);
        HBox.setMargin(leftVbox, new Insets(10, 10, 10, 20)); // set margin of left VBox in HBox

        VBox rightVbox = new VBox();
        rightVbox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVbox.setPrefSize(100, 200);

        Button registerButton = registerPageController.getRegisterButton();
        rightVbox.getChildren().add(registerButton);

        VBox.setMargin(registerButton, new Insets(0, 30, 30, 0)); // set margin of button in right VBox
        rightVbox.setPadding(new Insets(0, 30, 30, 0)); // set padding of right VBox

        hbox.getChildren().addAll(leftVbox, rightVbox);
        this.getChildren().addAll(label, hbox);

        HBox.setHgrow(leftVbox, Priority.ALWAYS); // set HGrow to fill half of HBox
        HBox.setHgrow(rightVbox, Priority.ALWAYS);
        VBox.setVgrow(hbox, Priority.ALWAYS);
    }
}
