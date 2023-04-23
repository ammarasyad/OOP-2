package com.tll.gui.model.bill;

import com.tll.gui.model.barang.Barang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    protected List<Pair<Barang, Integer>> cart;

}
