package com.tll.backend.model.barang;

import java.io.Serializable;

public class KategoriBarang implements Serializable, Cloneable {
    @Override
    public KategoriBarang clone() {
        try {
            return (KategoriBarang) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
