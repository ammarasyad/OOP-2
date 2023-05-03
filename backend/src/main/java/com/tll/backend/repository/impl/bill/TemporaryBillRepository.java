package com.tll.backend.repository.impl.bill;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.impl.InMemoryCrudRepository;

import java.util.HashMap;

public class TemporaryBillRepository extends InMemoryCrudRepository<Integer, TemporaryBill> {

    public TemporaryBillRepository() {
        super(new HashMap<>());
    }
}
