package com.tll.gui.models;

import com.tll.backend.model.user.Member;
import com.tll.gui.controllers.UpdatePageModel;
import javafx.util.StringConverter;

import java.util.Objects;

public class UpdatePageControl {
    UpdatePageModel updatePageModel;
    public UpdatePageControl(UpdatePageModel updatePageModel){
        this.updatePageModel = updatePageModel;
//        updatePageModel.getAccounts().setConverter(new StringConverter<>() {
//            @Override
//            public String toString(Member obj) {
//                if (obj == null)
//                    return "";
//                return obj.toString();
//            }
//
//            @Override
//            public Member fromString(String obj) {
//                for(Member member: updatePageModel.getMemberRepository().findAll()){
//                    if(member.toString().equals(obj)){
//                        return member;
//                    }
//                }
//                throw new RuntimeException("item in combobox is not valid");
//            }
//        });

        updatePageModel.getAccounts().valueProperty().addListener((obs, oldVal, newVal) -> {
            // Update the TextField text based on the selected value
            if (newVal == null) {
                return;
            }
            updatePageModel.getNameTextField().setText(newVal.getName());
            updatePageModel.getPhoneTextField().setText(newVal.getPhone());
            updatePageModel.setMemberId(newVal.getId());
            if(newVal.isActiveStatus()){
                updatePageModel.getActivity().getSelectionModel().select(0);
            } else {
                updatePageModel.getActivity().getSelectionModel().select(1);
            }
            if(Objects.equals(newVal.getType(), "VIP")) {
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
        updatePageModel.getAccounts().setDataItems(updatePageModel.getMemberRepository().findAll());

    }
}
