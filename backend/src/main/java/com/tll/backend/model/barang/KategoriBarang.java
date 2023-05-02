package com.tll.backend.model.barang;

import java.io.Serializable;

import com.tll.backend.repository.CloneableObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KategoriBarang implements CloneableObject<KategoriBarang>, Serializable {

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
