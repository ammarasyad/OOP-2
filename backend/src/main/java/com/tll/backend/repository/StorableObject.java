package com.tll.backend.repository;

public interface StorableObject<ID extends Comparable<ID>> {

    ID getId();

}
