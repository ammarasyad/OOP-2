package com.tll.backend.datastore.loader;

public class ObjDataLoader extends DataLoader {
    public ObjDataLoader(String fileName) {
        super(new ObjAdapter(fileName));
    }
}
