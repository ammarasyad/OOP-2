package com.tll.plugin;

import org.jfree.data.category.DefaultCategoryDataset;

public class TestChart {
    public static void main(String[] args) {
        String series1 = "Amuse";
        String series2 = "Park";

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
        dataset.addValue(205, series2, "Wednesday");
        dataset.addValue(235, series2, "Thursday");
        dataset.addValue(200, series2, "Friday");
        dataset.addValue(300, series2, "Saturday");
        dataset.addValue(330, series2, "Sunday");


        LineChart l = new LineChart("line", "x", "y", dataset);
        l.visualizeChart();

        BarChart b = new BarChart("bar", "x", "y", dataset);
        b.visualizeChart();
    }
}
