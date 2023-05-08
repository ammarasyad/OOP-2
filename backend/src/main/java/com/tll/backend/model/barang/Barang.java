package com.tll.backend.model.barang;

import com.tll.backend.repository.CloneableObject;
import com.tll.backend.repository.StorableObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Barang")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Barang implements Serializable, Cloneable, CloneableObject<Barang>, StorableObject<Integer> {

    @Id
    private Integer id;
    private int stok;
    private String nama;
    private BigDecimal harga;
    @Column(name = "harga_beli")
    private BigDecimal hargaBeli;
    @ManyToOne
    @JoinColumn(name = "id_kategori", nullable = false)
    private KategoriBarang kategori;
    @Column(name = "url_gambar")
    private String urlGambar;
    private boolean dijual;

    @Override
    public Barang clone() {
        try {
            return ((Barang) super.clone())
                                .setId(this.id)
                                .setStok(this.stok)
                                .setNama(this.nama) // String is immutable
                                .setHarga(this.harga) // big decimal is immutable
                                .setHargaBeli(this.hargaBeli)
                                .setKategori(this.kategori.clone())
                                .setUrlGambar(this.urlGambar)
                                .setDijual(dijual);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
