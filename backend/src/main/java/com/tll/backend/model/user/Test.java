package com.tll.backend.model.user;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Customer> listCustomer = new ArrayList<Customer>();
        ArrayList<Member> listMember = new ArrayList<Member>();


        Usersystem systemUser = new Usersystem(listCustomer, listMember);
        Order order1 = new Order(1000);
        Order order2 = new Order(10000);
        Order order3 = new Order(100000);

        systemUser.pay("",order1);
        systemUser.pay("",order2);
        systemUser.pay("",order1);
        systemUser.pay("",order3);

        systemUser.upgradeCustomer(1, "apink", "081", "Vip");
        systemUser.pay("",order3);
        systemUser.printAll();
        systemUser.pay("apink", order3);
        systemUser.updateMember("apink", "wooo", "", "");
        systemUser.printAll();
    }
}


