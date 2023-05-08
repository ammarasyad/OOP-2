package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tll.backend.model.barang.Barang;
import org.javatuples.Pair;

import java.util.List;

public abstract class TemporaryBillMixin {
    protected Integer id;

    @JsonDeserialize(using = ListPairDeserializer.class)
    protected List<Pair<Barang, Integer>> cart;

    @JsonCreator
    public TemporaryBillMixin(@JsonProperty("id") Integer id, @JsonProperty("cart") List<Pair<Barang, Integer>> cart) {
        this.id = id;
        this.cart = cart;
    }
}
