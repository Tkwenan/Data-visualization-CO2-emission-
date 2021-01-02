import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import javax.swing.*;  
import java.awt.Dimension;
import java.util.ArrayList;
/**
 *  A LineViewB class for displaying line chart b
 */
public class LineViewB extends View
{
    private static final String title = "Year-on-Year Change in CO2 Emissions";

    /**
     *  A Constructor for objects of class LineViewB. Intializes the GUI
     *  line Chart B
     */
    public LineViewB(XYDataset dataset) {
        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(title, // Chart
                "Year", // X-Axis Label
                "Change in CO2 Emissions since previous year (+ million t)", // Y-Axis Label
                dataset);

        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(600, 480));

        start.removeItemAt(0);
        end.removeItemAt(0);

        f.setTitle(title);
        f.add(chartPanel,  BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
    }
}

