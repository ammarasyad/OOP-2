package com.tll.gui.controllers;

import com.tll.backend.model.user.Member;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.AutoCompleteComboBox;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UpdatePageModel {
    private ComboBox<String> accountStatus;
    private ComboBox<String> activity;
    private AutoCompleteComboBox<Member> accounts;
    private MemberRepository memberRepository;
    private TextField nameTextField;
    private TextField phoneTextField;
    @Setter
    private Integer memberId;

    public UpdatePageModel(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
        accountStatus = new ComboBox<>();
        accountStatus.getItems().addAll("VIP", "Member");
        activity = new ComboBox<>();
        activity.getItems().addAll("Active", "Non-Active");
        nameTextField = new TextField();
        phoneTextField = new TextField();
        accounts = new AutoCompleteComboBox<>(memberRepository.findAll());
//        for(Member member : memberRepository.findAll()){
//            accounts.getItems().add(member);
//        }
//        accounts.setEditable(true);
//        accounts.setCellFactory(new Callback<ListView<Member>, ListCell<Member>>() {
//            @Override
//            public ListCell<Member> call(ListView<Member> listView) {
//                return new ListCell<Member>() {
//                    @Override
//                    protected void updateItem(Member item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty || item == null) {
//                            setText(null);
//                        } else {
//                            setText(item.toString()); // Set the text of the cell to the member's name
//                        }
//                    }
//                };
//            }
//        });
//
//        accounts.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null || newValue.isEmpty()) {
//                accounts.setItems(FXCollections.observableArrayList()); // Clear the suggestions if the editor is empty
//            } else {
//                List<Member> suggestions = getSuggestions(newValue); // Get the suggestions based on the typed string
//                accounts.setItems(FXCollections.observableArrayList(suggestions)); // Set the suggestions as the items of the combo box
//            }
//        });

    }
}
