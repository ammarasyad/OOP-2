package com.tll.gui.models;

import com.tll.gui.controllers.UpdatePageModel;
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

public class UpdatePageControl {
    UpdatePageModel updatePageModel;
    public UpdatePageControl(UpdatePageModel updatePageModel){
        this.updatePageModel = updatePageModel;
        updatePageModel.getAccounts().valueProperty().addListener((obs, oldVal, newVal) -> {
            // Update the TextField text based on the selected value
            updatePageModel.getNameTextField().setText(newVal.getName());
            updatePageModel.getPhoneTextField().setText(newVal.getPhone());
            updatePageModel.setMemberId(newVal.getId());
            if(newVal.isActiveStatus()){
                updatePageModel.getActivity().getSelectionModel().select(0);
            } else {
                updatePageModel.getActivity().getSelectionModel().select(1);
            }
            if(newVal.getType() == "VIP") {
                updatePageModel.getAccountStatus().getSelectionModel().select(0);
            } else {
                updatePageModel.getAccountStatus().getSelectionModel().select(1);
            }
        });
    }

    public void saveChanges(){
        updatePageModel.getMemberRepository().updateMember(updatePageModel.getMemberId(),
                                                            updatePageModel.getNameTextField().getText(),
                                                            updatePageModel.getPhoneTextField().getText(),
                                                            updatePageModel.getAccountStatus().getValue());

    }
}
