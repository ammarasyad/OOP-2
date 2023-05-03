package com.tll.backend.repository.impl.bill;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.repository.impl.InMemoryCrudRepository;

import java.util.HashMap;

public class FixedBillRepository extends InMemoryCrudRepository<Integer, FixedBill> {

    public FixedBillRepository() {
        super(new HashMap<>());
    }
}
