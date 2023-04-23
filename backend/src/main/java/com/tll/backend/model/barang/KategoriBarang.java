package com.tll.backend.model.barang;

public class KategoriBarang implements Cloneable {
    @Override
    public KategoriBarang clone() {
        try {
            return (KategoriBarang) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
