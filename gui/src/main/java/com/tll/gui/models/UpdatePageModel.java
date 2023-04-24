package com.tll.gui.models;

import com.tll.gui.controllers.UpdatePageController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UpdatePageModel extends VBox {
    UpdatePageController updatePageController;
    public UpdatePageModel(UpdatePageController updatePageController){
        super();
        this.updatePageController = updatePageController;
        this.setPrefSize(600, 400);
        this.setAlignment(Pos.TOP_LEFT);

        Label label = new Label("Update Account");
        label.setFont(new Font(38.0));
        VBox.setMargin(label, new Insets(0, 0, 0, 10)); // set left margin of label

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #ddd;");

        VBox leftVbox = new VBox();
        leftVbox.setPrefSize(100, 200);
        leftVbox.setSpacing(10);
        leftVbox.setPadding(new Insets(10));

        Label nameLabel = new Label("Nama :");
        TextField nameTextField = updatePageController.getNameTextField();
        Label phoneLabel = new Label("Nomor Telepon :");
        TextField phoneTextField = updatePageController.getPhoneTextField();
        Label statusLabel = new Label("Status Akun :");
        ComboBox<String> accountStatus = updatePageController.getAccountStatus();

        leftVbox.getChildren().addAll(nameLabel, nameTextField, phoneLabel, phoneTextField, statusLabel, accountStatus);
        HBox.setMargin(leftVbox, new Insets(10, 10, 10, 20)); // set margin of left VBox in HBox

        VBox rightVbox = new VBox();
//        VBox.setMargin(registerButton, new Insets(0, 30, 30, 0)); // set margin of button in right VBox
        rightVbox.setPadding(new Insets(10, 30, 30, 0)); // set padding of right VBox

        VBox bottomRightVbox = new VBox();
        bottomRightVbox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomRightVbox.setPrefSize(100, 200);

        Button registerButton = updatePageController.getRegisterButton();
        bottomRightVbox.getChildren().add(registerButton);

        VBox topRightVbox = new VBox();
        topRightVbox.setAlignment(Pos.TOP_LEFT);
        topRightVbox.setPadding(new Insets(10, 10, 0, 20));

        Label akunLabel = new Label("Pilih Akun :");
        ComboBox<String> accounts = updatePageController.getAccounts();
        accounts.setMaxWidth(1.7976931348623157E308);
        accounts.setPrefWidth(2.0);
//        accounts.prefWidthProperty().bind(topRightVbox.prefWidthProperty());

//        VBox.setVgrow(accounts, Priority.ALWAYS);
        topRightVbox.getChildren().addAll(akunLabel, accounts);

        rightVbox.getChildren().addAll(topRightVbox, bottomRightVbox);
        hbox.getChildren().addAll(leftVbox, rightVbox);
        this.getChildren().addAll(label, hbox);

        VBox.setVgrow(bottomRightVbox, Priority.ALWAYS);
        VBox.setVgrow(topRightVbox, Priority.ALWAYS);
        HBox.setHgrow(leftVbox, Priority.ALWAYS); // set HGrow to fill half of HBox
        HBox.setHgrow(rightVbox, Priority.ALWAYS);
        VBox.setVgrow(hbox, Priority.ALWAYS);
    }
}
