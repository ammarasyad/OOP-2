package com.tll.gui.model.bill;

import com.tll.gui.model.barang.Barang;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
public class FixedBill extends Bill {

    public FixedBill(List<Pair<Barang, Integer>> listBarang) {
        super(new ArrayList<>(listBarang));
    }

}
