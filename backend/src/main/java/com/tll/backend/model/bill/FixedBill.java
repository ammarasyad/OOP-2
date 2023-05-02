package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.CloneableObject;
import com.tll.backend.repository.StorableObject;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javatuples.Pair;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FixedBill extends Bill implements StorableObject<Integer>, CloneableObject<FixedBill> {

    public FixedBill(Integer id, Integer userId, List<Pair<Barang, Integer>> listBarang) {
        super(id, userId, listBarang.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .toList());
    }

    @Override
    public FixedBill clone() {
        try {
            var fixedCart = this.cart.stream()
                    .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                    .toList();

            return (FixedBill) ((Bill) super.clone())
                    .setId(this.id)
                    .setUserId(this.userId)
                    .setCart(fixedCart);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
