package com.tll.backend.datastore;


import java.io.Serializable;

@lombok.Data
public abstract class Data implements Serializable {
    protected Object data;
}
