package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.StorableObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javatuples.Pair;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemporaryBill extends Bill implements StorableObject<Integer> {

    public TemporaryBill(Integer id, Integer userId) {
        super(id, userId, new ArrayList<>());
    }

    public void addToBill(Barang barang, int jumlah) {
        cart.add(Pair.with(barang, jumlah));
    }

    public FixedBill convertToFixedBill() {
        return new FixedBill(this.id, this.userId, cart);
    }

}
