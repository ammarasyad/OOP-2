package com.tll.backend.repository.impl.barang;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.CrudRepository;
import com.tll.backend.repository.InMemoryRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BarangRepository implements CrudRepository<Integer, Barang>, InMemoryRepository<Barang> {

    // main storage for local repository
    private List<Barang> storage;

    // for indexing, points to index of the actual object in storage
    private Map<Integer, Integer> idMap;
    private Map<String, List<Integer>> namaMap;
    private Map<BigDecimal, List<Integer>> hargaMap;

    @Override
    public void delete(Barang entity) {
        // remove from index
        int idBarang = entity.getId(), indexBarang = idMap.getOrDefault(idBarang, -1);

        if (indexBarang == -1)
            return;

        idMap.remove(idBarang);
        removeFromListMap(namaMap, entity.getNama(), idBarang);
        removeFromListMap(hargaMap, entity.getHarga(), idBarang);

        storage.remove(indexBarang);
    }

    @Override
    public void deleteById(Integer idBarang) {
        // remove from index
        int indexBarang = idMap.getOrDefault(idBarang, -1);

        if (indexBarang == -1)
            return;

        Barang barang = storage.get(idMap.get(idBarang));
        idMap.remove(idBarang);
        removeFromListMap(namaMap, barang.getNama(), idBarang);
        removeFromListMap(hargaMap, barang.getHarga(), idBarang);

        storage.remove(indexBarang);
    }

    private <T> void removeFromListMap(Map<T, List<Integer>> map, T mapKey, Integer storageIndex) {
        List<Integer> listMap = map.get(mapKey);
        listMap.remove(storageIndex);
    }

    @Override
    public Iterable<Barang> findAll() {
        return new ArrayList<>(storage);
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

    public Iterable<Barang> findByNama(String nama) {
        return Optional.of(namaMap.get(nama))
                .orElseGet(ArrayList::new)
                .stream().map(el -> storage.get(el).clone())
                .collect(Collectors.toList());
    }

    public Iterable<Barang> findByHarga(BigDecimal harga) {
        return Optional.of(hargaMap.get(harga))
                .orElseGet(ArrayList::new)
                .stream().map(el -> storage.get(el).clone())
                .collect(Collectors.toList());
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
