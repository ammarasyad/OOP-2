package com.tll.gui.models;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.impl.barang.BarangRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InsertBarangControl {
    private final BarangRepository barangRepository;

    public void addToBarangRepository(@NonNull Barang barang){
        if (barang.getId() != null){
            return;
        }
        barangRepository.save(barang);
    }
}
