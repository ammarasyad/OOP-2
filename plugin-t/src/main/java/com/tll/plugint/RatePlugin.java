package com.tll.plugint;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.bill.Bill;
import com.tll.gui.ClosableTab;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class RatePlugin extends Plugin {

    private static final String LIST_FILE_PATH = "src/main/resources/list-rate-plugin.json";
    private static final String FILE_PATH = "src/main/resources/mata-uang-state.json";
    private static final JsonAdapter jsonListAdapter = new JsonAdapter(LIST_FILE_PATH);
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);
    private static final String TAB_NAME = "Mata uang";

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private MenuItem menuItem;
    private HashMap<Integer, MataUang> mataUangHolder;
    private State state;

    @Override
    public void load() {
        if (!loadFile()) {
            return;
        }
        menuItem = new MenuItem(TAB_NAME);
        menuItem.setOnAction(el -> openRatePage());
        var button = new Button();
        button.setText(state.isEnabled() ? "disable " : "enable " + "rate");
        button.setOnAction(el -> {
            if (!state.isEnabled()) {
                button.setText("enable rate");
                state.setEnabled(false);
                removeMenu();
                return;
            }
            button.setText("disable rate");
            state.setEnabled(true);
            addMenu();
        });
        if (state.isEnabled()) {
            addMenu();
        }
    }

    private void addMenu() {
        appController.getPages().getItems().add(menuItem);
    }

    private void removeMenu() {
        int i = 0;
        var items = appController.getPages().getItems();
        for (var item: items) {
            if (item.hashCode() == menuItem.hashCode()) {
                items.remove(i);
                return;
            }
            ++i;
        }
    }

    private void openRatePage() {
        VBox ratePage = new VBox();

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
        });

        ratePage.getChildren().addAll(idBox, button);

        ClosableTab closableTab = new ClosableTab(TAB_NAME);
        closableTab.setContent(ratePage);

        appController.getTabPane().getTabs().add(closableTab);
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