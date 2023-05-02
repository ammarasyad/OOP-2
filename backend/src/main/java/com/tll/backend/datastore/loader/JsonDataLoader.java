package com.tll.backend.datastore.loader;

public class JsonDataLoader extends DataLoader {
    public JsonDataLoader(String fileName) {
        super(new JsonAdapter(fileName));
    }
}
