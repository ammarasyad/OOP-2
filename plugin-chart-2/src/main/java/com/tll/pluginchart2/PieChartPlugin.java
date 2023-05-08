package com.tll.pluginchart2;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.Bill;
import com.tll.gui.ClosableTab;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

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

        HBox mainBox = new HBox();

        VBox pluginInfoBox = new VBox();

        Label nameLabel = new Label("Pie Chart");
        Label statusLabel = new Label("Plugin Enable");

        pluginInfoBox.getChildren().addAll(nameLabel, statusLabel);
        pluginInfoBox.setSpacing(10);

        Button unplugButton = new Button();
        unplugButton.setText(additionState.isEnabled() ? "Disable" : "Enable");
        unplugButton.setOnAction(el -> {
            if (additionState.isEnabled()) {
                unplugButton.setText("Enable");
                statusLabel.setText("Plugin Disable");
                additionState.setEnabled(false);
                removeMenu();
            }
            else {
                unplugButton.setText("Disable");
                statusLabel.setText("Plugin Enable");
                additionState.setEnabled(true);
                addMenu();
            }
        });

        mainBox.getChildren().addAll(pluginInfoBox, unplugButton);
        mainBox.setSpacing(20);
        mainBox.setPadding(new Insets(10));
        mainBox.setStyle("-fx-border-color: black;");
        mainBox.setAlignment(Pos.BASELINE_CENTER);

        if (additionState.isEnabled()) {
            addMenu();
        }

        appController.getPluginNodes().add(mainBox);
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
        appController.getBarangRepository().findAll().forEach(el -> chart.getData().add(new PieChart.Data(el.getNama() + "(id: " + el.getId() + ")", el.getStok())));
        AppController.getCommonScheduledThreadPool().scheduleAtFixedRate(() -> Platform.runLater(() -> {
            for (Barang barang : appController.getBarangRepository().findAll()) {
                boolean found = false;
                for (PieChart.Data data : chart.getData()) {
                    if (data.getName().equals(barang.getNama() + "(id: " + barang.getId() + ")")) {
                        data.setPieValue(barang.getStok());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    chart.getData().add(new PieChart.Data(barang.getNama() + "(id: " + barang.getId() + ")", barang.getStok()));
                }
            }
        }), 1, 1, TimeUnit.SECONDS);

        chartVBox.getChildren().add(chart);

        ClosableTab tab = new ClosableTab("Pie Chart");
        tab.setContent(chartVBox);
        appController.getTabPane().getTabs().add(tab);
    }
}
