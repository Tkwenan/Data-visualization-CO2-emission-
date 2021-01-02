import org.jfree.chart.*;
import javax.swing.*;  
import java.awt.event.*; 
import org.jfree.data.category.CategoryDataset;
import java.util.ArrayList;
import org.jfree.data.general.PieDataset;
/**
 *  A Controller Class which intializes the Dashboard and is responsible
 *  for adding functionality to the GUI buttons, mediating the interaction 
 *  between user and view/model.
 */
public class Controller{
    Model model;
    View barChart;
    View lineChartA;
    View pieChart;
    View lineChartB;

    /**
     *  A Constructor for objects of class Controller
     */
    public Controller(){
        try{
            model = new Model();
        }catch(Exception io){

        }
    }

    /**
     *  A Main method that lauches the Dashboard and intilializes the
     *  Dashboard's interactive buttons
     */
    public static void main(){
        Controller c = new Controller();
        c.lineChartA = new LineViewA(c.model.dataA);
        
        c.model.barData(1902, 2018);
        c.barChart = new BarView((CategoryDataset)c.model.barData);

        c.model.pieData(2018);
        c.pieChart = new PieView((PieDataset)c.model.pieData);

        c.model.lineChartBData();
        c.lineChartB = new LineViewB(c.model.dataB);

        // make zoom buttons:
        c.lineChartA.zoomIn.setActionCommand(ChartPanel.ZOOM_IN_DOMAIN_COMMAND);
        c.lineChartA.zoomIn.addActionListener(c.lineChartA.chartPanel);
        c.lineChartA.zoomOut.setActionCommand(ChartPanel.ZOOM_OUT_DOMAIN_COMMAND);
        c.lineChartA.zoomOut.addActionListener(c.lineChartA.chartPanel);
        c.lineChartA.zoom.setActionCommand(ChartPanel.ZOOM_RESET_DOMAIN_COMMAND);
        c.lineChartA.zoom.addActionListener(c.lineChartA.chartPanel);
        c.lineChartB.zoomIn.setActionCommand(ChartPanel.ZOOM_IN_DOMAIN_COMMAND);
        c.lineChartB.zoomIn.addActionListener(c.lineChartB.chartPanel);
        c.lineChartB.zoomOut.setActionCommand(ChartPanel.ZOOM_OUT_DOMAIN_COMMAND);
        c.lineChartB.zoomOut.addActionListener(c.lineChartB.chartPanel);
        c.lineChartB.zoom.setActionCommand(ChartPanel.ZOOM_RESET_DOMAIN_COMMAND);
        c.lineChartB.zoom.addActionListener(c.lineChartB.chartPanel);

        //make select region button:
        c.makeRegionSelectButtons();

        // make date selection buttons:
        c.makeDateSelectButtons();

        // make chart selection buttons:
        c.makeChartSelectButton(c.lineChartA);
        c.makeChartSelectButton(c.barChart);
        c.makeChartSelectButton(c.pieChart);
        c.makeChartSelectButton(c.lineChartB);
    }

    /**
     *  A method for intializing the region select buttons of 
     *  line charts A and B
     */
    public void makeRegionSelectButtons(){
        lineChartA.regionSelect.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    lineChartA.currentRegSelection = new ArrayList();
                    for (JCheckBox checkBox : lineChartA.boxes) {
                        if (checkBox.isSelected()){
                            lineChartA.currentRegSelection.add(checkBox.getText());
                        }
                    }
                    model.selectRegionsA(lineChartA.currentRegSelection);
                    lineChartA.chartPanel.repaint();
                }
            } );

        lineChartB.regionSelect.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    lineChartB.currentRegSelection = new ArrayList();
                    for (JCheckBox checkBox : lineChartB.boxes) {
                        if (checkBox.isSelected()){
                            lineChartB.currentRegSelection.add(checkBox.getText());
                        }
                    }
                    model.selectRegionsB(lineChartB.currentRegSelection);
                    lineChartB.chartPanel.repaint();
                }
            } );
    }

     /**
     *  A method for intializing the date selection buttons
     */
    public void makeDateSelectButtons(){
        pieChart.dateSelect.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent e )
                {
                    int a = Integer.parseInt(pieChart.start.getSelectedItem().toString());
                    model.pieData(a);
                    pieChart.pieChart.setTitle("Global CO2 Emission in " + a);

                }});

        barChart.dateSelect.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent e )
                {
                    int a = Integer.parseInt(barChart.start.getSelectedItem().toString());
                    int b = Integer.parseInt(barChart.end.getSelectedItem().toString());;
                    barChart.errorLabel.setText("");
                    if(a < b)
                        model.barData(a, b);
                    else
                        barChart.errorLabel.setText("Please select a valid interval");
                }});

        lineChartA.dateSelect.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent e )
                {
                    int a = Integer.parseInt(lineChartA.start.getSelectedItem().toString());
                    int b = Integer.parseInt(lineChartA.end.getSelectedItem().toString());
                    lineChartA.errorLabel.setText("");
                    if(a < b){
                        a -= 1902;
                        b -= 1902;
                        model.selectDateA(a, b);
                        lineChartA.chartPanel.repaint();
                    }
                    else
                        lineChartA.errorLabel.setText("Please select a valid interval");
                }});

        lineChartB.dateSelect.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent e )
                {
                    int a = Integer.parseInt(lineChartB.start.getSelectedItem().toString());
                    int b = Integer.parseInt(lineChartB.end.getSelectedItem().toString());
                    lineChartB.errorLabel.setText("");
                    if(a < b){
                        a -= 1903;
                        b -= 1903;
                        model.selectDateB(a, b);
                        lineChartB.chartPanel.repaint();
                    }
                    else
                        lineChartB.errorLabel.setText("Please select a valid interval");
                }});

    }

     /**
     *  A method for intializing the chart select buttons of the
     *  Dashboard
     */
    public void makeChartSelectButton(View v){
        v.viewSelect.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent e )
                {
                    String chart = v.chartList.getSelectedItem().toString();
                    if(chart.equals("Line Chart A")){
                        lineChartA.f.setVisible(true);
                        barChart.f.hide();
                        pieChart.f.hide();
                        lineChartB.f.hide();
                    }
                    else if(chart.equals("Bar Chart")){
                        barChart.f.setVisible(true);
                        lineChartA.f.hide();
                        pieChart.f.hide();
                        lineChartB.f.hide();
                    }
                    else if(chart.equals("Pie Chart")){
                        pieChart.f.setVisible(true);
                        lineChartA.f.hide();
                        barChart.f.hide();
                        lineChartB.f.hide();
                    }
                    else if(chart.equals("Line Chart B")){
                        lineChartB.f.setVisible(true);
                        lineChartA.f.hide();
                        barChart.f.hide();
                        pieChart.f.hide();
                    }
                }
            } );
    }

}
