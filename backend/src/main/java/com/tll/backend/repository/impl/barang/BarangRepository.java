package com.tll.backend.repository.impl.barang;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.impl.InMemoryCrudRepository;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BarangRepository extends InMemoryCrudRepository<Integer, Barang> {

    // for indexing
    private final Map<String, List<Barang>> namaMap;
    private final Map<BigDecimal, List<Barang>> hargaMap;

    public BarangRepository() {
        super(new HashMap<>());
        namaMap = new HashMap<>();
        hargaMap = new HashMap<>();
    }

    @Override
    public void delete(@NotNull Barang entity) {
        // remove from index
        int idBarang = entity.getId();

        if (!storage.containsKey(idBarang))
            return;

        Barang barang = storage.get(idBarang);
        barang.setDijual(false);
    }

    @Override
    public void deleteById(Integer idBarang) {
        // remove from index
        if (!storage.containsKey(idBarang))
            return;

        Barang barang = storage.get(idBarang);
        barang.setDijual(false);
    }

    @Override
    public <S extends Barang> S save(@NotNull S entity) {
        int idBarang = entity.getId();
        // new object
        if (!storage.containsKey(idBarang)) {
            entity.setId(storage.size());
        } else {
            Barang barang = storage.get(idBarang);
            if (!barang.getHarga().equals(entity.getHarga())) {
                List<Barang> listMap = hargaMap.get(barang.getHarga());
                listMap.remove(barang);
                addToListMap(hargaMap, barang.getHarga(), barang);
            }
            if (!barang.getNama().equals(entity.getNama())) {
                List<Barang> listMap = namaMap.get(barang.getNama());
                listMap.remove(barang);
                addToListMap(namaMap, barang.getNama(), barang);
            }
        }
        return super.save(entity);
    }

    public Iterable<Barang> findByNama(String nama) {
        return Optional.of(namaMap.get(nama))
                .orElseGet(ArrayList::new);
    }

    public Iterable<Barang> findByHarga(BigDecimal harga) {
        return Optional.of(hargaMap.get(harga))
                .orElseGet(ArrayList::new);
    }

    private <T, V> void addToListMap(Map<T, List<V>> map, T mapIndex, V entity) {
        List<V> listMap = map.getOrDefault(mapIndex, null);

        if (listMap == null) {
            List<V> newList = new ArrayList<>();
            newList.add(entity);
            map.put(mapIndex, newList);
            return;
        }

        listMap.add(entity);
    }

    private <T, V> void removeFromListMap(Map<T, List<V>> map, T mapKey, V storageIndex) {
        List<V> listMap = map.get(mapKey);
        listMap.remove(storageIndex);
    }

}
