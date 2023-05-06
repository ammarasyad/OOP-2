package com.tll.gui;

import com.tll.backend.model.user.Member;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AutoCompleteComboBox<E extends Object> extends ComboBox<E> {
    @Setter
    private Iterable<E> dataItems;
    public AutoCompleteComboBox(Iterable<E> dataItems){
        super();
        this.dataItems = dataItems;
        for(E member : dataItems){
            getItems().add(member);
        }
        setEditable(true);
        setCellFactory(new Callback<ListView<E>, ListCell<E>>() {
            @Override
            public ListCell<E> call(ListView<E> listView) {
                return new ListCell<E>() {
                    @Override
                    protected void updateItem(E item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.toString()); // Set the text of the cell to the member's name
                        }
                    }
                };
            }
        });

        getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                setItems(FXCollections.observableArrayList()); // Clear the suggestions if the editor is empty
            } else {
                List<E> suggestions = getSuggestions(newValue); // Get the suggestions based on the typed string
                setItems(FXCollections.observableArrayList(suggestions)); // Set the suggestions as the items of the combo box
            }
        });

        setConverter(new StringConverter<>() {
            @Override
            public String toString(E obj) {
                if (obj == null)
                    return "";
                return obj.toString();
            }

            @Override
            public E fromString(String obj) {
                for(E member: dataItems){
                    if(member.toString().equals(obj)){
                        return member;
                    }
                }
                throw new RuntimeException("item in combobox is not valid");
            }
        });

    }

    private List<E> getSuggestions(String input) {
        List<E> suggestions = new ArrayList<>();
        for (E member : dataItems) {
            if (member.toString().toLowerCase().startsWith(input.toLowerCase())) {
                suggestions.add(member);
            }
        }
        return suggestions;
    }
}
