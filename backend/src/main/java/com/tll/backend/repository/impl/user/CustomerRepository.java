package com.tll.backend.repository.impl.user;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.repository.impl.InMemoryCrudRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CustomerRepository extends InMemoryCrudRepository<Integer, Customer> {

    public CustomerRepository() {
        super(new HashMap<>());
    }

    public List<Integer> getAllCustomerId() {
        return storage.keySet().stream().map(el -> storage.get(el).getId()).toList();
    }

    public int getLargestId() {
        Customer largestCustomer = storage.keySet().stream()
                .map(storage::get)
                .max(Comparator.comparing(Customer::getId)).orElse(new Customer(0));

        return largestCustomer.getId();
    }

    public void customerPay(FixedBill bill) {
        Customer cust = new Customer(getLargestId()+1, bill);
        save(cust);
    }
}
