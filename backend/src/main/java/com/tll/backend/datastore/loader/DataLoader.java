package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public abstract class DataLoader {
    private final DataStore dataStore;

    public void save(List<?> objects) throws IOException {
        dataStore.save(objects);
    }

    public <T> List<T> load(final JavaType clazz) throws IOException {
        return dataStore.load(clazz);
    }
}
