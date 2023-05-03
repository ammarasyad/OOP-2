package com.tll.backend.model.user;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.repository.StorableObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer implements StorableObject<Integer> {
    private final Integer id;
    private FixedBill bill;

    public void printInfo() {
        System.out.println(getId() + " Bill: " + getBill());
    }
}
