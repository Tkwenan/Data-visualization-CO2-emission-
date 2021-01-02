import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.PieDataset;
import javax.swing.BorderFactory;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
/**
 *  A class PieView for displaying the pie chart
 */
public class PieView extends View
{
    private static final String title = "Regional Percentage % of Global CO2 Emissions";
    
    /**
     *  A Constructor for objects of class pieView. Intializes the GUI pie Chart
     *  @param dataset - the pie dataset
     */
    public PieView(PieDataset dataset) {
        pieChart = ChartFactory.createPieChart("Global CO2 Emission in 2018", 
                dataset,
                true,
                true,
                false);
                
        chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(600, 480));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        PiePlot plot = (PiePlot) pieChart.getPlot();
        StandardPieSectionLabelGenerator spilg = new StandardPieSectionLabelGenerator("{2}" );
        plot.setLabelGenerator(spilg);

        
        dateSelectPanel.remove(end);
        dateSelectPanel.remove(startLabel);
        dateSelectPanel.remove(endLabel);
        
        zoomPanel.remove(zoom);
        zoomPanel.remove(zoomIn);
        zoomPanel.remove(zoomOut);
        
        f.remove(menuPanel);
        
        f.setTitle(title);
        f.add(chartPanel,  BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
}
}