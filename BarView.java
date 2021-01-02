import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
/**
 *  A barView class for displaying a barChart
 */
public class BarView extends View{
    private static final String title = "Average Growth Rate of C02 Emissions over a Time Interval";

    /**
     *  A Constructor for objects of class barView. Intializes the GUI
     *  bar Chart.
     */
    public BarView(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(title, // Chart
                "", // X-Axis Label
                "Percentage Change (%)", // Y-Axis Label
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(600, 480));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        zoomPanel.remove(zoomIn);
        zoomPanel.remove(zoomOut);
        zoomPanel.remove(zoom);
        f.remove(menuPanel);
        
        f.setTitle(title);
        f.add(chartPanel,  BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
    }
} 