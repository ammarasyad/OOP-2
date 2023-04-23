package com.tll.gui.model.bill;

import com.tll.gui.model.barang.Barang;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FixedBill extends Bill {

    public FixedBill(List<Pair<Barang, Integer>> listBarang) {
        super(listBarang.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .toList());
    }

}
