package com.tll.gui.controllers;

import com.tll.backend.model.user.Customer;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.AutoCompleteComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class RegisterPageModel {
    private TextField nameTextField;
    private TextField phoneTextField;
    private AutoCompleteComboBox<Customer> accounts;
    private CustomerRepository customerRepository;
    private MemberRepository memberRepository;

    public RegisterPageModel(CustomerRepository customerRepository, MemberRepository memberRepository){
        this.customerRepository = customerRepository;
        this.memberRepository = memberRepository;
        nameTextField = new TextField();
        phoneTextField = new TextField();
        accounts = new AutoCompleteComboBox(customerRepository.findAll());
    }
}
