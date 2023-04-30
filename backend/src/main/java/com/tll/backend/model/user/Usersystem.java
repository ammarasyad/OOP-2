package com.tll.backend.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;

@Getter
@AllArgsConstructor
public class Usersystem {
    private ArrayList<Customer> listCustomer;
    private ArrayList<Member> listMember;

    public long getLargestId() {
        Customer largestCustomer = listCustomer.stream().max(Comparator.comparing(Customer::getId)).orElse(new Customer(0));
        Member largestMember = listMember.stream().max(Comparator.comparing(Member::getId)).orElse(new Member(0));

        if (largestCustomer.getId() > largestMember.getId()) {
            return largestCustomer.getId();
        }
        return largestMember.getId();
    }
    public ArrayList<Long> getCustomerId() {
        ArrayList<Long> listId = new ArrayList<Long>();
        for (int i = 0; i < getListCustomer().size(); i++) {
            listId.add(getListCustomer().get(i).getId());
        }
        return listId;
    }

    public ArrayList<String> getMemberName() {
        ArrayList<String> listName = new ArrayList<String>();
        for (int i = 0; i < getListMember().size(); i++) {
            listName.add(getListMember().get(i).getName());
        }
        return listName;
    }

    public Customer searchCustomer(long id) {
        return listCustomer.stream().filter(customer -> (id == customer.getId())).findAny().get();
    }

    public Member searchMember(String name) {
        return listMember.stream().filter(member -> name.equals(member.getName())).findAny().get();
    }

    public void upgradeCustomer(long id, String name, String phone, String type) {
        Customer customer = searchCustomer(id);
        ArrayList<Order> custOrder= new ArrayList<Order>();
        custOrder.add(customer.getOrders());
        Member member = new Member(customer.getId(), type, true, name, phone, custOrder, 0);

        this.listCustomer.remove(customer);
        this.listMember.add(member);
    }

    public void updateMember(String memberName, String newName, String newPhone, String newType) {
        searchMember(memberName).updateMember(newName, newPhone, newType);
    }

    public void changeActivationStatus(String name) {
        searchMember(name).changeActivation();
    }

    public void pay(String name, Order order) {
        if (name.isEmpty()) {
            Customer cust = new Customer(getLargestId()+1, order);
            this.listCustomer.add(cust);
        }
        else {
            searchMember(name).addOrder(order);
        }
    }
    public void printAll() {
        for (int i = 0; i < getListCustomer().size(); i++) {
            getListCustomer().get(i).printInfo();
        }
        for (int i = 0; i < getListMember().size(); i++) {
            getListMember().get(i).printInfo();
        }
    }
}
