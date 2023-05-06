package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.CloneableObject;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javatuples.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FixedBill extends Bill implements CloneableObject<FixedBill> {

    @Setter(AccessLevel.PRIVATE)
    private Integer userId;

    public FixedBill(Integer id, Integer userId, List<Pair<Barang, Integer>> listBarang) {
        super(id, listBarang.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .collect(Collectors.toList()));
        this.userId = userId;
    }

    @Override
    public FixedBill clone() {
        try {
            var fixedCart = this.cart.stream()
                    .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                    .toList();

            return ((FixedBill) ((Bill) super.clone())
                    .setId(this.id)
                    .setCart(fixedCart))
                    .setUserId(this.userId);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
