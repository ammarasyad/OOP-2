package com.tll.backend.repository.impl;

import com.tll.backend.repository.CrudRepository;
import com.tll.backend.repository.StorableObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class InMemoryCrudRepository<ID extends Comparable<ID>, V extends StorableObject<ID>> implements CrudRepository<ID, V> {

    protected Map<ID, V> storage;

    @Override
    public void delete(V entity) {
        ID id = entity.getId();
        storage.remove(id);
    }

    @Override
    public void deleteById(ID id) {
        V entity = storage.get(id);

        if (entity == null) {
            return;
        }
        storage.remove(id);
    }

    @Override
    public Iterable<V> findAll() {
        return storage.keySet().stream()
                .map(el -> storage.get(el))
                .toList();
    }

    @Override
    public Optional<V> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public <S extends V> S save(S entity) {
        ID id = entity.getId();
        if (storage.containsKey(id)) {
            storage.replace(id, entity);
        } else {
            storage.put(id, entity);
        }
        return entity;
    }
}
