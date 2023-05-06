package com.tll.backend.datastore.loader.hibernate;

import com.tll.backend.datastore.DataStore;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class HibernateAdapter implements DataStore {

    private final HibernateDataStore hibernateDataStore;

    @Override
    public void save(List<?> objects) throws IOException {
        hibernateDataStore.saveAll(objects);
    }

    @Override
    public <T> List<T> load(Class<T> clazz) {
        return hibernateDataStore.getAll(clazz);
    }

}
