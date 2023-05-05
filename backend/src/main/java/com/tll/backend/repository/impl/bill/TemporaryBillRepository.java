package com.tll.backend.repository.impl.bill;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.InMemoryCrudRepository;

import java.util.Comparator;
import java.util.HashMap;

public class TemporaryBillRepository extends InMemoryCrudRepository<Integer, TemporaryBill> {

    public TemporaryBillRepository() {
        super(new HashMap<>());
    }

    public int getNextId() {
        return storage.keySet().stream()
                .map(el -> storage.get(el).getId())
                .max(Comparator.comparing(el -> el)).orElse(1) + 1;
    }

}
