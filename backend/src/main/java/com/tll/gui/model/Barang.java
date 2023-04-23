package com.tll.gui.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Barang implements Serializable {

    private Integer id;
    private int stok;
    private String nama;
    private BigDecimal harga;
    private BigDecimal hargaBeli;
    private KategoriBarang kategori;
    private String urlGambar;
    private boolean dijual;

}
