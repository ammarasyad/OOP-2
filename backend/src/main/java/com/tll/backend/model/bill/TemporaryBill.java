package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javatuples.Pair;

import java.util.ArrayList;

@Data

@EqualsAndHashCode(callSuper = true)
public class TemporaryBill extends Bill {

    public TemporaryBill(Integer id) {
        super(id, new ArrayList<>());
    }

    public void addToBill(Barang barang, int jumlah) {
        cart.add(Pair.with(barang, jumlah));
    }

    public FixedBill convertToFixedBill(int userId) {
        var tempCart = cart.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .toList();

        return new FixedBill(this.id, userId, tempCart);
    }

}
