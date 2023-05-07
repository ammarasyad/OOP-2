package com.tll.pluginchart2;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.gui.ClosableTab;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class PieChartPlugin extends Plugin {
    private static final String FILE_PATH = "src/main/resources/addition-plugin-state.json";
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);

    private static final String TAB_NAME = "Pie Chart";

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private AdditionState additionState;
    private MenuItem menuItem;

    @Override
    public void load() {
        menuItem = new MenuItem(TAB_NAME);
        menuItem.setOnAction(el -> openPieChartPage());
        additionState = new AdditionState(true);

        var button = new Button();
        button.setText(additionState.isEnabled() ? "disable" : "enable" + " add");
        button.setOnAction(el -> {
            if (!additionState.isEnabled()) {
                button.setText("enable add");
                additionState.setEnabled(false);
                Bill.setPriceAddition(new BigDecimal(0));
                removeMenu();
                return;
            }
            button.setText("disable add");
            additionState.setEnabled(true);
            addMenu();
        });

        if (additionState.isEnabled()) {
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

    private void openPieChartPage() {
        VBox chartVBox = new VBox();
        chartVBox.setAlignment(Pos.CENTER);

        //Create a PieChart
        PieChart chart = new PieChart();

        chart.setTitle("Data Barang");
        chart.setLegendSide(Side.BOTTOM);

        //Add Data to PieChart
        BarangRepository barangRepository = appController.getBarangRepository();
        for (Barang barang : barangRepository.findAll()) {
            chart.getData().add(new PieChart.Data(barang.getNama(), barang.getStok()));
        }

        chartVBox.getChildren().add(chart);

        ClosableTab tab = new ClosableTab("Pie Chart");
        tab.setContent(chartVBox);
        appController.getTabPane().getTabs().add(tab);
    }
}
