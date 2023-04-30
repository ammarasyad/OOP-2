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
public class Member {
    private final long id;
    private String type;
    private boolean activeStatus;
    private String name;
    private String phone;
    private ArrayList<Order> orders;
    private long point;

    public void updateMember(String name, String phone, String type) {
        if (!name.isEmpty()) {
            setName(name);
        }
        if (!phone.isEmpty()) {
            setPhone(phone);
        }
        if (!type.isEmpty()) {
            setType(type);
        }
    }

    public void changeActivation() {
        if (isActiveStatus()) {
            setActiveStatus(false);
        }
        else setActiveStatus(true);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        setPoint((long) (order.getPrice()*0.1));
    }
    public void printInfo() {
        System.out.println(getId() + " " + getType() + " " + getName() + " " + getPoint());
        getOrders().forEach(order -> System.out.println("Order: " + order.getPrice()));
    }

}
