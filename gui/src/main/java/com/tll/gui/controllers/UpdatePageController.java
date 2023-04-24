package com.tll.gui.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class UpdatePageController {
    private TextField nameTextField;
    private TextField phoneTextField;
    private Button registerButton;
    private ComboBox<String> accountStatus;
    private ComboBox<String> accounts;
    public UpdatePageController(){
        nameTextField = new TextField();
        nameTextField.setPromptText("e.g. Hayam Wuruk");
        phoneTextField = new TextField();
        phoneTextField.setPromptText("e.g. 081806122004");
        accountStatus = new ComboBox<>();
        accountStatus.getItems().addAll("VIP", "Member");
        accountStatus.setValue("Member");
        accounts = new ComboBox<>();
        accounts.getItems().addAll("123902 - Budiman Senjougahara", "135212 - Neko Subyanto");
        accounts.setValue("135212 - Neko Subyanto");
        accounts.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Update the TextField text based on the selected value
            nameTextField.setText(newVal);
        });
        registerButton = new Button("Update");
    }
}
