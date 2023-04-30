package com.tll.backend.model.bill;

import com.tll.backend.model.barang.Barang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill implements Serializable {

    protected Integer userId;
    protected List<Pair<Barang, Integer>> cart;

}
