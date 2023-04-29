package com.tll.backend.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonDataAdapter<I extends Data, O extends Data> implements IDataAdapter<O> {
    private final I data;

    @Override
    public O convert() {
        return null;
    }
}
