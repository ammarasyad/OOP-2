package com.tll.backend.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member extends Customer{
    private boolean activeStatus;
    private String phone;
    private long point;

    public Member(long id, String type, String name, String phone) {
        setId(id);
        setType(type);
        setName(name);
        setPhone(phone);
    }

    public void updateMember(String name, String phone, String type) {
        setType(type);
        setName(name);
        setPhone(phone);
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
    @Override
    public void printInfo() {
        System.out.println(getId());
        System.out.println(getType());
        System.out.println(getName());
    }

}
