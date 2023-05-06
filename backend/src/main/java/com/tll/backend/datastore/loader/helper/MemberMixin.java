package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tll.backend.model.bill.FixedBill;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class MemberMixin {
    private final Integer id;
    private String type;
    private boolean activeStatus;
    private String name;
    private String phone;
    private List<FixedBill> bills;
    private long point;

    @JsonCreator
    public MemberMixin(@JsonProperty("id") Integer id,
                       @JsonProperty("type") String type,
                       @JsonProperty("activeStatus") boolean activeStatus,
                       @JsonProperty("name") String name,
                       @JsonProperty("phone") String phone,
                       @JsonProperty("bills") List<FixedBill> bills,
                       @JsonProperty("point") long point) {
        this.id = id;
        this.type = type;
        this.activeStatus = activeStatus;
        this.name = name;
        this.phone = phone;
        this.bills = bills;
        this.point = point;
    }
}
