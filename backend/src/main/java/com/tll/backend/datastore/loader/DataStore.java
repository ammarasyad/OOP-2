package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;

import java.io.IOException;
import java.util.List;

public interface DataStore {
    void save(List<?> objects) throws IOException;
    <T> List<T> load(final JavaType clazz) throws IOException;
}
