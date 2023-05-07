package com.tll.backend.datastore.loader.hibernate;

import com.tll.backend.datastore.DataStore;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class HibernateAdapter implements DataStore {

    private final HibernateDataStore hibernateDataStore;

    public static void main(String[] args) {
        HibernateAdapter hibernateAdapter = new HibernateAdapter(new HibernateDataStore());
        List<FixedBill> list = hibernateAdapter.load(FixedBill.class);
        list.forEach(el -> {
            System.out.println(el.getId());
        });
    }

    @Override
    public void save(List<?> objects) throws IOException {
        hibernateDataStore.saveAll(objects);
    }

    @Override
    public <T> List<T> load(Class<T> clazz) {
        return hibernateDataStore.getAll(clazz);
    }

}
