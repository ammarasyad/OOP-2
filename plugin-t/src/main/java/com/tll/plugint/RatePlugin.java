package com.tll.plugint;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class RatePlugin extends Plugin {

    private static final String LIST_FILE_PATH = "list-rate-plugin.json";
    private static final String FILE_PATH = "mata-uang-state.json";
    private static final JsonAdapter jsonListAdapter = new JsonAdapter(LIST_FILE_PATH);
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private HashMap<Integer, MataUang> mataUangHolder;
    private State state;

    @Override
    public void load() {
        if (!loadFile()) {
            return;
        }

        VBox rateVBox = new VBox();

        ComboBox<Integer> idBox = new ComboBox<>();
        idBox.getItems().addAll(mataUangHolder.keySet());

        var button = new Button("save rate");
        button.setOnAction(el -> {
            var baseRate = BigDecimal.valueOf(mataUangHolder.get(state.getIdMataUang()).getAbsoluteRate());
            var newRate = BigDecimal.valueOf(mataUangHolder.get(idBox.getValue()).getAbsoluteRate());

            appController.getBarangRepository().findAll().forEach(e -> {
                e.setHarga(e.getHarga().divide(baseRate, RoundingMode.CEILING).multiply(newRate));
                e.setHargaBeli(e.getHargaBeli().divide(baseRate, RoundingMode.CEILING).multiply(newRate));
            });

            var fixedBillRepo = appController.getFixedBillRepository();

            fixedBillRepo.findAll().forEach(e -> {
                e.getCart().forEach(item -> {
                    var barang = item.getValue0();
                    barang.setHarga(barang.getHarga().divide(baseRate, RoundingMode.CEILING).multiply(newRate));
                    barang.setHargaBeli(barang.getHargaBeli().divide(baseRate, RoundingMode.CEILING).multiply(newRate));
                });
            });
            state.setIdMataUang(idBox.getValue());
            saveFile();
        });

        rateVBox.getChildren().addAll(idBox, button);
        appController.getPluginNodes().add(rateVBox);

    }

    private void saveFile() {
        try {
            jsonAdapter.save(List.of(state));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean loadFile() {
        mataUangHolder = new HashMap<>();
        try {
            var res = jsonListAdapter.load(MataUang.class);
            res.forEach(el -> mataUangHolder.put(el.getId(), el));
            var resx = jsonAdapter.load(State.class);
            if (res.size() < 1) {
                return false;
            }
            state = resx.get(0);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}