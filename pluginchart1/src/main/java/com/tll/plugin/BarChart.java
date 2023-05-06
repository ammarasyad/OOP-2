package com.tll.plugin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
@AllArgsConstructor

public class BarChart extends JFrame {
    private String title;
    private String xLabel;
    private String yLabel;
    private DefaultCategoryDataset dataset;

    private void createChart() {
        JFreeChart chart = ChartFactory.createBarChart(
                getTitle(),
                getXLabel(),
                getYLabel(),
                getDataset()
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void visualizeChart() {
        this.createChart();
        this.setVisible(true);
    }
}
