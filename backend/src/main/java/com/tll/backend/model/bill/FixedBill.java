package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javatuples.Pair;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FixedBill extends Bill {

    public FixedBill(List<Pair<Barang, Integer>> listBarang) {
        super(listBarang.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .toList());
    }

}
