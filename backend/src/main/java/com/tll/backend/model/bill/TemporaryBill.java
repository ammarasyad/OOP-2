package com.tll.backend.model.bill;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tll.backend.datastore.loader.helper.TemporaryBillDeserializer;
import com.tll.backend.model.barang.Barang;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "temporary_bill")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonDeserialize(using = TemporaryBillDeserializer.class)
public class TemporaryBill extends Bill {

    public TemporaryBill(Integer id) {
        super(id, new ArrayList<>());
    }

    public void addToBill(Barang barang, int jumlah) {
        cart.add(Pair.with(barang, jumlah));
    }

    public void emptyBill() {
        cart.clear();
    }

    public void removeFromBill(Barang barang) {
        for (int i = 0; i < cart.size(); i++) {
            if (Objects.equals(cart.get(i).getValue0().getId(), barang.getId())) {
                cart.remove(i);
                return;
            }
        }
    }

    public FixedBill convertToFixedBill(int userId) {
        var tempCart = cart.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .toList();

        return new FixedBill(this.id, userId, tempCart);
    }

    public void setTemporaryCart(List<Pair<Barang, Integer>> cart) {
        this.cart = cart;
    }
}
