package com.tll.backend.model.barang;

import java.io.Serializable;

import com.tll.backend.repository.CloneableObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kategori_barang")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KategoriBarang implements Cloneable, CloneableObject<KategoriBarang>, Serializable {

    @Id
    private Integer id;
    @Column(name = "nama")
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
