package com.tll.plugin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.fx.ChartViewer;


public class LineChartFx extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public static JFreeChart createChart() {

        String series1 = "Amusement Park";
        String series2 = "Museum";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(245, series1, "Monday");
        dataset.addValue(290, series1, "Tuesday");
        dataset.addValue(230, series1, "Wednesday");
        dataset.addValue(280, series1, "Thursday");
        dataset.addValue(345, series1, "Friday");
        dataset.addValue(355, series1, "Saturday");
        dataset.addValue(380, series1, "Sunday");

        dataset.addValue(210, series2, "Monday");
        dataset.addValue(220, series2, "Tuesday");
        dataset.addValue(205,  series2, "Wednesday");
        dataset.addValue(235, series2, "Thursday");
        dataset.addValue(200, series2, "Friday");
        dataset.addValue(300, series2, "Saturday");
        dataset.addValue(330, series2, "Sunday");

        JFreeChart lineChart = ChartFactory.createLineChart(
                "JFreeChart Line Chart", // Chart title
                "Day",                   // X-Axis Label
                "Visitors",              // Y-Axis Label
                dataset                  // Dataset for the Chart
        );

        return lineChart;
    }

    @Override
    public void start(Stage stage) throws Exception {

        ChartViewer viewer = new ChartViewer(createChart());
        stage.setScene(new Scene(viewer));
        stage.setTitle("JFreeChart: LineChart");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }
}
