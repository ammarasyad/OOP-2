package com.tll.backend.repository.impl.bill;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.repository.CrudRepository;
import com.tll.backend.repository.InMemoryRepository;

import java.util.Optional;

public class FixedBillRepository implements CrudRepository<Integer, FixedBill>,
        InMemoryRepository<FixedBill> {

    @Override
    public void delete(FixedBill entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Iterable<FixedBill> findAll() {
        return null;
    }

    @Override
    public Optional<FixedBill> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public <S extends FixedBill> S save(S entity) {
        return null;
    }

    @Override
    public void loadData(String url) {

    }
}
