package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tll.backend.model.barang.Barang;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.List;

public class ListPairDeserializer extends StdDeserializer<List<Pair<Barang, Integer>>> {
    public ListPairDeserializer() {
        this(null);
    }

    protected ListPairDeserializer(Class<Pair<Barang, Integer>> t) {
        super(t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Pair<Barang, Integer>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializationContext.readValue(jsonParser, List.class);
    }
}
