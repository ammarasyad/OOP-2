package com.tll.gui.factory;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;

import java.math.BigDecimal;

public class PageActionFactory {

    public static void doInsertBarang( String stok, String nama, String harga, String hargaBeli, String idKategori,
                               String url, boolean dijual ){


        //int idInt = Integer.parseInt(id);
        int stokInt = Integer.parseInt(stok);
        BigDecimal hargaBigDecimal = new BigDecimal(harga);
        BigDecimal hargaBeliBigDecimal = new BigDecimal(hargaBeli);
        int idKategoriInt = Integer.parseInt(idKategori);

        // TODO: get Kategori from repository

        Barang barang = new Barang(1, stokInt, nama, hargaBigDecimal,
                hargaBeliBigDecimal, new KategoriBarang(idKategoriInt,"aaa"), url, dijual);

        System.out.println(url);

    }
}
