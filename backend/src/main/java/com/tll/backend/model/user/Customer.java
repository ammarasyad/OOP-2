package com.tll.backend.model.user;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.repository.StorableObject;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Customer")
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer implements Serializable, StorableObject<Integer> {

    @Id
    private final Integer id;

    @Override
    public String toString() {
        return "Customer " + id;
    }

    @OneToOne
    @JoinColumn(name = "id_bill")
    private FixedBill bill;

    public void printInfo() {
        System.out.println(getId() + " Bill: " + getBill());
    }
}
