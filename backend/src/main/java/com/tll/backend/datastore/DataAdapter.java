package com.tll.backend.datastore;

import com.fasterxml.jackson.databind.JavaType;

import java.io.IOException;
import java.util.List;

public interface DataAdapter {
    void save(List<?> objects) throws IOException;
    <T> List<T> load(final JavaType clazz) throws IOException;
}
