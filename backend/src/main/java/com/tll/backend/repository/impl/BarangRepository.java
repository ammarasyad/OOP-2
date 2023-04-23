package com.tll.backend.repository.impl;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.CrudRepository;
import com.tll.backend.repository.InMemoryFileRepository;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BarangRepository implements CrudRepository<Integer, Barang>, InMemoryFileRepository<Barang> {

    // main storage for local repository
    private List<Barang> storage;

    // for indexing, points to index of the actual object in storage
    private Map<Integer, Integer> idMap;
    private Map<String, Integer> namaMap;

    @Override
    public void delete(Barang entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Iterable<Barang> findAll() {
        return null;
    }

    @Override
    public Optional<Barang> finById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public <S extends Barang> S save(S entity) {
        return null;
    }

    @Override
    public void loadData(String url) {

    }

}
