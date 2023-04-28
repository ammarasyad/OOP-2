package com.tll.backend.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Customer {
    protected long id;
    protected String type;
    protected String name;
    protected ArrayList<Order> orders;
    protected static long idCounter = 0;

    public Customer() {
        setId(++idCounter);
        setType("Customer");
        setName("");
        this.orders = new ArrayList<Order>();
    }

    public Customer(Order order) {
        setId(++idCounter);
        setType("Customer");
        setName("");
        this.orders = new ArrayList<Order>();
        this.orders.add(order);
    }

    public void printInfo() {
        System.out.println(getId());
    }
}
