package com.tll.gui.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class RegisterPageController {
    private TextField nameTextField;
    private TextField phoneTextField;
    private Button registerButton;
    public RegisterPageController(){
        nameTextField = new TextField();
        nameTextField.setPromptText("e.g. Hayam Wuruk");
        phoneTextField = new TextField();
        phoneTextField.setPromptText("e.g. 081806122004");
        registerButton = new Button("Register");
    }
}
