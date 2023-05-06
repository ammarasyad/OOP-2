package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tll.backend.model.barang.Barang;
import org.javatuples.Pair;

@JsonIgnoreProperties(value = "size", ignoreUnknown = true)
public abstract class PairMixin {
    @JsonCreator
    public static Pair<Barang, Integer> with(@JsonProperty("barang") Object barang, @JsonProperty("jumlah") Object jumlah) {
        return Pair.with((Barang) barang, (Integer) jumlah);
    }
}
