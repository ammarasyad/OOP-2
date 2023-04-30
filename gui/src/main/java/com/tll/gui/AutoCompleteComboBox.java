package com.tll.gui;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AutoCompleteComboBox extends ComboBox<String> {
    private String[] dataItems;
    public AutoCompleteComboBox(String[] dataItems){
        super();
        this.setEditable(true);
        this.dataItems = dataItems;

        TextField editor = this.getEditor();
        editor.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(editor.getText());
            this.getItems().setAll(filterData(newValue));
        });
    }

    public AutoCompleteComboBox(){
        this(null);
    }

    private String[] filterData(String input) {
        String lowerCaseInput = input.toLowerCase();
        return FXCollections.observableArrayList(dataItems)
                .filtered(item -> item.toLowerCase().startsWith(lowerCaseInput))
                .toArray(String[]::new);
    }
}
