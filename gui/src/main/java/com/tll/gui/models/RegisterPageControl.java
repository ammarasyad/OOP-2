package com.tll.gui.models;

import com.tll.backend.model.user.Customer;
import com.tll.gui.controllers.RegisterPageModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RegisterPageControl {
    RegisterPageModel registerPageModel;
    public RegisterPageControl(RegisterPageModel registerPageModel){
        this.registerPageModel = registerPageModel;
    }

    public void createMember() {
        Customer customer = registerPageModel.getAccounts().getValue();
        registerPageModel.getMemberRepository().addMember(customer, registerPageModel.getNameTextField().getText(), registerPageModel.getPhoneTextField().getText(), "Member");
        registerPageModel.getCustomerRepository().delete(customer);
        registerPageModel.getAccounts().getSelectionModel().clearSelection();
        registerPageModel.getAccounts().setDataItems(registerPageModel.getCustomerRepository().findAll());
        registerPageModel.getNameTextField().setText("");
        registerPageModel.getPhoneTextField().setText("");
    }
}
