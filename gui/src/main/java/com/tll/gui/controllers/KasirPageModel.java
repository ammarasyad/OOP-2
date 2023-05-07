package com.tll.gui.controllers;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.AutoCompleteComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import com.tll.backend.model.user.Member;
import lombok.Getter;

@Getter
public class KasirPageModel {
    private TemporaryBill temporaryBill;
    private MemberRepository memberRepository;
    private FlowPane productsList;
    private VBox selectedItem;
    private AutoCompleteComboBox<Member> members;
    private CheckBox useMember;
    private Label billStatus;

    public KasirPageModel(TemporaryBill temporaryBill, MemberRepository memberRepository){
        this.temporaryBill = temporaryBill;
        this.memberRepository = memberRepository;
        members = new AutoCompleteComboBox<>(memberRepository.findAll());
        useMember = new CheckBox();
        productsList = new FlowPane();
        selectedItem = new VBox();
        billStatus = new Label();
    }
}
