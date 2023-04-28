package com.tll.backend.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order {
    private long price;
    public Order(long price) {
       setPrice(price);
    }
}
