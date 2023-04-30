package com.tll.backend.repository.impl.bill;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.repository.CrudRepository;
import com.tll.backend.repository.InMemoryRepository;

import java.util.Optional;

public class TemporaryBillRepository implements CrudRepository<Integer, TemporaryBill>,
        InMemoryRepository<TemporaryBill> {

    @Override
    public void delete(TemporaryBill entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Iterable<TemporaryBill> findAll() {
        return null;
    }

    @Override
    public Optional<TemporaryBill> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public <S extends TemporaryBill> S save(S entity) {
        return null;
    }

    @Override
    public void loadData(String url) {

    }
}
