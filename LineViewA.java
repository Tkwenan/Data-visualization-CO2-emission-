import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import javax.swing.*;  
import java.awt.Dimension;
/**
 *  A class LineViewA for displaying the line chart A
 */
public class LineViewA extends View{
    private static final String title = "CO2 Emissions by Economic Region";

    /**
     *  A Constructor for objects of class LineViewA. Intializes the GUI
     *  line Chart A.
     */
    public LineViewA(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(title, // Chart
                "Year", // X-Axis Label
                "CO2 Emissions (billion tons)", // Y-Axis Label
                dataset);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 480));
        chartPanel.setDisplayToolTips( true );

        f.add(chartPanel,  BorderLayout.CENTER);
        f.pack();
        f.setTitle(title);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}  