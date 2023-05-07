package com.tll.pluginchart1;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.gui.ClosableTab;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.chart.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.embed.swing.SwingNode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.NoArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
public class LineBarPlugin extends Plugin {
    private static final String FILE_PATH = "src/main/resources/addition-plugin-state.json";
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);
    private static final String TAB_NAME = "Line Chart";

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

        var button = new Button();
        button.setText(additionState.isEnabled() ? "disable" : "enable" + " add");
        button.setOnAction(el -> {
            if (!additionState.isEnabled()) {
                button.setText("enable add");
                additionState.setEnabled(false);
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
        appController.getPages().getItems().addAll(lineMenuItem, barMenuItem);
    }

    private void removeMenu() {
        int i = 0;
        var items = appController.getPages().getItems();
        for (var item: items) {
            if (item.hashCode() == lineMenuItem.hashCode()) {
                items.remove(i);
                return;
            }
            if (item.hashCode() == barMenuItem.hashCode()) {
                items.remove(i);
                return;
            }
            ++i;
        }
    }

    private void openLineChartPage() {
        VBox chartVBox = new VBox();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Stok");
        xAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 18));
        yAxis.setLabel("Harga");
        yAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Create a BarChart with the CategoryAxis and NumberAxis
        LineChart<Number, Number> chart = new LineChart(xAxis, yAxis);

        chart.setTitle("Data Barang");

        // Create a series and add some data to it
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        BarangRepository barangRepository = appController.getBarangRepository();
        for (Barang barang : barangRepository.findAll()) {
            series.getData().add(new XYChart.Data<>(barang.getStok(), barang.getHarga()));
        }

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

        BarangRepository barangRepository = appController.getBarangRepository();
        for (Barang barang : barangRepository.findAll()) {
            series.getData().add(new XYChart.Data<>(barang.getNama(), barang.getStok()));
        }

        // Add the series to the chart
        chart.getData().add(series);

        chartVBox.getChildren().add(chart);

        ClosableTab tab = new ClosableTab("Bar Chart");
        tab.setContent(chartVBox);
        appController.getTabPane().getTabs().add(tab);
    }

}
