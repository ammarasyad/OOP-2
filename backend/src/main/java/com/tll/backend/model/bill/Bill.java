package com.tll.backend.model.bill;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tll.backend.datastore.loader.helper.BillSerializer;
import com.tll.backend.datastore.loader.helper.ListPairDeserializer;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.StorableObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javatuples.Pair;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonSerialize(using = BillSerializer.class)
public class Bill implements Serializable, StorableObject<Integer> {

    protected Integer id;

    @JsonDeserialize(using = ListPairDeserializer.class)
    protected List<Pair<Barang, Integer>> cart;

    public BigDecimal getTotalPrice() {
        return cart.stream().map(el -> el.getValue0().getHarga().multiply(BigDecimal.valueOf(el.getValue1())))
                .reduce(new BigDecimal(0), BigDecimal::add, BigDecimal::add);
    }

}
