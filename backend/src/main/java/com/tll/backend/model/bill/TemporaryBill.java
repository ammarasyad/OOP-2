package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javatuples.Pair;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemporaryBill extends Bill{

    public TemporaryBill() {
        super(new ArrayList<>());
    }

    public void addToBill(Barang barang, int jumlah) {
        cart.add(Pair.with(barang, jumlah));
    }

    public FixedBill convertToFixedBill() {
        return new FixedBill(cart);
    }

}
