package com.tll.backend.repository.impl;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.CrudRepository;
import com.tll.backend.repository.InMemoryFileRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class BarangRepository implements CrudRepository<Integer, Barang>, InMemoryFileRepository<Barang> {

    // main storage for local repository
    private List<Barang> storage;

    // for indexing, points to index of the actual object in storage
    private Map<Integer, Integer> idMap;
    private Map<String, List<Integer>> namaMap;
    private Map<BigDecimal, List<Integer>> hargaMap;

    @Override
    public void delete(Barang entity) {
        int idBarang = entity.getId(), indexBarang = idMap.getOrDefault(idBarang, -1);

        if (indexBarang == -1)
            return;

        storage.remove(indexBarang);
        idMap.remove(idBarang);
    }

    @Override
    public void deleteById(Integer idBarang) {
        int indexBarang = idMap.getOrDefault(idBarang, -1);

        if (indexBarang == -1)
            return;

        storage.remove(indexBarang);
        idMap.remove(idBarang);
    }

    @Override
    public Iterable<Barang> findAll() {
        return List.copyOf(storage);
    }

    @Override
    public Optional<Barang> findById(Integer idBarang) {
        // clone so storage barang in storage doesnt change until save is called
        return Optional.of(storage.get(idMap.get(idBarang)).clone());
    }

    @Override
    public <S extends Barang> S save(S entity) {
        int idBarang = entity.getId(), indexBarang = idMap.getOrDefault(idBarang, -1);
        // new object
        if (indexBarang == -1) {
            entity.setId(storage.size());
            storage.add(entity.clone());

        } else {
            Barang barang = storage.get(indexBarang);
            if (!barang.getHarga().equals(entity.getHarga())) {
                List<Integer> listMap = hargaMap.get(barang.getHarga());
                listMap.remove(indexBarang);
                addToListMap(hargaMap, barang.getHarga(), idBarang);
            }
            if (!barang.getNama().equals(entity.getNama())) {
                List<Integer> listMap = namaMap.get(barang.getNama());
                listMap.remove(indexBarang);
                addToListMap(namaMap, barang.getNama(), idBarang);
            }

            storage.set(indexBarang, entity.clone());
        }
        return entity;
    }

    @Override
    public void loadData(String url) {
        // next iteration
    }

    private <T> void addToListMap(Map<T, List<Integer>> map, T mapIndex, Integer storageIndex) {
        List<Integer> listMap = map.getOrDefault(mapIndex, null);

        if (listMap == null) {
            List<Integer> newList = new ArrayList<>();
            newList.add(storageIndex);
            map.put(mapIndex, newList);
            return;
        }

        listMap.add(storageIndex);

    }

}
