package com.tll.backend.datastore;

import java.io.IOException;
import java.util.List;

public interface DataStore {
    void save(List<?> objects) throws IOException;
    <T> List<T> load(final Class<T> clazz) throws IOException;
}
