package com.tll.backend.user;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Usersystem<T extends Customer> {
    private ArrayList<T> listCustomer;

    public Usersystem(ArrayList listCustomer) {
        this.listCustomer = new ArrayList<T>();
    }

    public ArrayList<Long> getCustomerId() {
        ArrayList<Long> listId = new ArrayList<Long>();
        for (int i = 0; i < getListCustomer().size(); i++) {
            if (getListCustomer().get(i).getType().equals("Customer")) {
                listId.add(getListCustomer().get(i).getId());
            }
        }
        return listId;
    }

    public ArrayList<String> getMemberName() {
        ArrayList<String> listName = new ArrayList<String>();
        for (int i = 0; i < getListCustomer().size(); i++) {
            if (!getListCustomer().get(i).getType().equals("Customer")) {
                listName.add(getListCustomer().get(i).getName());
            }
        }
        return listName;
    }

    public int searchCustomer(long id) {
        for (int i = 0; i < getListCustomer().size(); i++) {
            if (getListCustomer().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public long searchMember(String name) {
        for (int i = 0; i < getListCustomer().size(); i++) {
            if (getListCustomer().get(i).getName() == name) {
                return getListCustomer().get(i).getId();
            }
        }
        return -1;
    }

    public void upgradeCustomer(long id, String name, String phone, String type) {
        int custIdx = searchCustomer(id);
        T customer = getListCustomer().get(custIdx);

        Member member = new Member(id, type, name, phone);
        this.listCustomer.remove(custIdx);
        this.listCustomer.add((T) member);
    }

    public void updateMember(long id, String name, String phone, String type) {
        int custIdx = searchCustomer(id);
        Member member = (Member) getListCustomer().get(custIdx);
        member.updateMember(name, phone, type);
    }

    public void changeActivationStatus(long id) {
        int custIdx = searchCustomer(id);
        Member member = (Member) getListCustomer().get(custIdx);
        member.changeActivation();
    }

    public void pay(String name, Order order) {
        if (name.isEmpty()) {
            Customer cust = new Customer(order);
            this.listCustomer.add((T) cust);
        }
        else {
            long id = searchMember(name);
            int idx = searchCustomer(id);
            Member member = (Member) getListCustomer().get(idx);
            member.addOrder(order);
        }
    }
    public void printAll() {
        for (int i = 0; i < getListCustomer().size(); i++) {
            getListCustomer().get(i).printInfo();
        }
    }
}
