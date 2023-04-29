package com.tll.backend.datastore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class XmlData extends Data {
    private static final long serialVersionUID = 1L;

    //    private final Object data;
    public XmlData(Object data) {
        this.data = data;
    }
}
