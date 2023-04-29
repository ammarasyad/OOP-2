package com.tll.backend.datastore;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class XmlDataAdapter<I extends Data, O extends Data> implements IDataAdapter<O> {
    private final I data;

    @Override
    public O convert() {
        return null;
    }
}
