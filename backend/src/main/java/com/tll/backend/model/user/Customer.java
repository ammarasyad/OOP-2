package com.tll.backend.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
    private final long id;
    private Order orders;

    public void printInfo() {
        System.out.println(getId() + " Order: " + getOrders().getPrice());
    }
}
