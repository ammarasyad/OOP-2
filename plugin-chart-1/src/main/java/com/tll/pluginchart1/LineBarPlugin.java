package com.tll.pluginchart1;

import com.tll.backend.model.barang.Barang;
import com.tll.gui.ClosableTab;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
public class LineBarPlugin extends Plugin {
//    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2, runnable -> {
//        Thread thread = new Thread(runnable);
//        thread.setDaemon(true);
//        return thread;
//    });

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private AdditionState additionState;

    private MenuItem lineMenuItem;
    private MenuItem barMenuItem;

    @Override
    public void load() {
        lineMenuItem = new MenuItem("Line Chart");
        lineMenuItem.setOnAction(el -> openLineChartPage());

        barMenuItem = new MenuItem("Bar Chart");
        barMenuItem.setOnAction(el -> openBarChartPage());
        additionState = new AdditionState(true);

        HBox mainBox = new HBox();

        VBox pluginInfoBox = new VBox();

        Label nameLabel = new Label("Line and Bar Chart");
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
                removeLineMenu();
                removeBarMenu();
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
        appController.getPages().getItems().add(lineMenuItem);
        appController.getPages().getItems().add(barMenuItem);
    }

    private void removeLineMenu() {
        int i = 0;
        var items = appController.getPages().getItems();
        for (var item: items) {
            if (item.hashCode() == lineMenuItem.hashCode()) {
                items.remove(i);
                return;
            }
            ++i;
        }
    }

    private void removeBarMenu() {
        int i = 0;
        var items = appController.getPages().getItems();
        for (var item: items) {
            if (item.hashCode() == barMenuItem.hashCode()) {
                items.remove(i);
                return;
            }
            ++i;
        }
    }

    private void openLineChartPage() {
        VBox chartVBox = new VBox();
        chartVBox.setAlignment(Pos.CENTER);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Stok");
        xAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 18));
        yAxis.setLabel("Harga");
        yAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Create a BarChart with the CategoryAxis and NumberAxis
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        chart.setTitle("Data Barang");

        // Create a series and add some data to it
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        appController.getBarangRepository().findAll().forEach(barang -> series.getData().add(new XYChart.Data<>(barang.getStok(), barang.getHarga())));
        AppController.getCommonScheduledThreadPool().scheduleAtFixedRate(() -> Platform.runLater(() -> {
            for (Barang barang : appController.getBarangRepository().findAll()) {
                boolean found = false;
                for (XYChart.Data<Number, Number> data : series.getData()) {
                    if (data.getXValue().equals(barang.getStok())) {
                        data.setYValue(barang.getHarga());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    series.getData().add(new XYChart.Data<>(barang.getStok(), barang.getHarga()));
                }
            }
        }), 1, 1, TimeUnit.SECONDS);

        // Add the series to the chart
        chart.getData().add(series);

        chartVBox.getChildren().addAll(new Pane(), chart, new Pane());
        chartVBox.setMinWidth(500);
        ClosableTab tab = new ClosableTab("Line Chart");
        tab.setContent(chartVBox);
        appController.getTabPane().getTabs().add(tab);
    }

    private void openBarChartPage() {
        VBox chartVBox = new VBox();
        chartVBox.setAlignment(Pos.CENTER);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Nama Barang");
        xAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 18));
        yAxis.setLabel("Stok");
        yAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Create a BarChart with the CategoryAxis and NumberAxis
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        chart.setTitle("Data Barang");

        // Create a series and add some data to it
        XYChart.Series<String, Number> series = new XYChart.Series<>();


        appController.getBarangRepository().findAll().forEach(barang -> series.getData().add(new XYChart.Data<>(barang.getNama() + "(id: " + barang.getId() + ")", barang.getStok())));

        AppController.getCommonScheduledThreadPool().scheduleAtFixedRate(() -> Platform.runLater(() -> {
            for (Barang barang : appController.getBarangRepository().findAll()) {
                boolean found = false;
                for (XYChart.Data<String, Number> data : series.getData()) {
                    if (data.getXValue().equals(barang.getNama() + "(id: " + barang.getId() + ")")) {
                        data.setYValue(barang.getStok());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    series.getData().add(new XYChart.Data<>(barang.getNama() + "(id: " + barang.getId() + ")", barang.getStok()));
                }
            }
        }), 1, 1, TimeUnit.SECONDS);

        // Add the series to the chart
        chart.getData().add(series);

        chartVBox.getChildren().add(chart);

        ClosableTab tab = new ClosableTab("Bar Chart");
        tab.setContent(chartVBox);
        appController.getTabPane().getTabs().add(tab);
    }

}
