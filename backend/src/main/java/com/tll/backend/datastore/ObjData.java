package com.tll.backend.datastore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObjData extends Data {
    private static final long serialVersionUID = 1L;

    //    private final Object data;
    public ObjData(Object data) {
        this.data = data;
    }
}
