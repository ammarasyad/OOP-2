package com.tll.gui.controllers;

import com.tll.gui.AutoCompleteComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class UpdatePageModel {
    private ComboBox<String> accountStatus;
    private ComboBox<String> activity;
    private AutoCompleteComboBox accounts;
    public UpdatePageModel(){
        accountStatus = new ComboBox<>();
        accountStatus.getItems().addAll("VIP", "Member");
        activity = new ComboBox<>();
        activity.getItems().addAll("Active", "Non-Active");
        accounts = new AutoCompleteComboBox(new String[]{"123902 - Budiman Senjougahara", "135212 - Neko Subyanto"});
    }
}
