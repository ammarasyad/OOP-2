package com.tll.backend.model.barang;

import com.tll.backend.repository.CloneableObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KategoriBarang implements CloneableObject<KategoriBarang> {

    private Integer id;
    private String namaKategori;

    @Override
    public KategoriBarang clone() {
        try {
            return (KategoriBarang) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
