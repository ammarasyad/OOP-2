package com.tll.gui.model.barang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class Barang implements Serializable, Cloneable {

    private Integer id;
    private int stok;
    private String nama;
    private BigDecimal harga;
    private BigDecimal hargaBeli;
    private KategoriBarang kategori;
    private String urlGambar;
    private boolean dijual;

    @Override
    public Barang clone() {
        try {
            Barang clone = ((Barang) super.clone())
                                .setId(this.id)
                                .setStok(this.stok)
                                .setNama(this.nama) // String is immutable
                                .setHarga(this.harga) // big decimal is immutable
                                .setHargaBeli(this.hargaBeli)
                                .setKategori(this.kategori.clone())
                                .setUrlGambar(this.urlGambar)
                                .setDijual(dijual);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
