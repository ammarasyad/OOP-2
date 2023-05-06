package com.tll.gui.controllers;

import com.tll.backend.model.user.Customer;
import com.tll.backend.repository.impl.user.CustomerRepository;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class RegisterPageModel {
    private CustomerRepository customerRepository;

    public RegisterPageModel(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;

    }
}
