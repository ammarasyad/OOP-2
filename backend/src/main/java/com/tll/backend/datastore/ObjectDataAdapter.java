package com.tll.backend.datastore;

import lombok.AllArgsConstructor;

import java.lang.reflect.ParameterizedType;

@AllArgsConstructor
public class ObjectDataAdapter<I extends Data, O extends Data> implements IDataAdapter<O> {
    private final I data;

    @Override
    public O convert() {
        return null;
    }
}
