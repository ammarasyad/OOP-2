package com.tll.backend.repository;

import java.util.Optional;

public interface CrudRepository<ID, V> {

    void delete(V entity);

    void deleteById(ID id);

    Iterable<V> findAll();

    Optional<V> finById(ID id);

    <S extends V> S save(S entity);

}
