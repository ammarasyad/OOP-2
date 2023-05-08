package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tll.backend.model.bill.FixedBill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CustomerMixin {
    private final Integer id;
    private final FixedBill bill;
    @JsonCreator
    public CustomerMixin(@JsonProperty("id") Integer id, @JsonProperty("bill") FixedBill bill) {
        this.id = id;
        this.bill = bill;
    }
}
