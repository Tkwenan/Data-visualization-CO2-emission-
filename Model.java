import java.util.HashMap;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
/**
 *  A Model Class containing the Dashboard data
 */
public class Model
{
    HashMap mapA;
    HashMap reducedMapA;
    HashMap mapB;
    HashMap reducedMapB;
    TimeSeriesCollection dataA;
    TimeSeriesCollection dataB;
    DefaultCategoryDataset barData = new DefaultCategoryDataset();
    DefaultPieDataset pieData = new DefaultPieDataset();
    final ArrayList<String> cacheData = new ArrayList();
    final ArrayList<String> countries = new ArrayList();

    /**
     * Constructor for objects of class Model. Initializes the Dashboard's 
     * opening chart, Line Chart A.
     */
    public Model() throws IOException
    {
        countries.add("Oceania");
        countries.add("China");
        countries.add("Africa");
        countries.add("India");
        countries.add("North America (excl. USA)");
        countries.add("Asia (excl. China & India)");
        countries.add("South America");
        countries.add("Europe (excl. EU-28)");
        countries.add("EU-28");
        countries.add("United States"); 

        mapA = new HashMap<String, TimeSeries>();
        for( String c : countries){
            mapA.put( c, new TimeSeries(c));
        }

        dataA = new TimeSeriesCollection();
        BufferedReader reader = new BufferedReader(new FileReader("/home/flucchetti/Desktop/cs203-f20-team6/annual-co-emissions-by-region.csv"));

        String row = reader.readLine();
        while ((row = reader.readLine()) != null){
            String[] data = row.split(",");

            double emissions = 0;
            try{
                emissions = Double.parseDouble(data[3]);
            }catch(Exception e){
                System.out.println("Wrong number format!");
            }

            int year = Integer.parseInt(data[2]);
            if(emissions != 0 && year >= 1902 && mapA.keySet().contains(data[0])){
                TimeSeries tm = (TimeSeries)mapA.get(data[0]);
                tm.add(new Day(1, 1 ,year), emissions);
                cacheData.add(row); // save selected data in cache
            }

        }

        for (Object tm : mapA.values()){
            dataA.addSeries((TimeSeries) tm);
        }

        reducedMapA = (HashMap)mapA.clone();
    }

    /**
     * A method that initializes Line Chart B.
     */
    public void lineChartBData(){
        dataB = new TimeSeriesCollection();
        mapB = new HashMap<String, TimeSeries>();
        
        for(String c : countries){
            mapB.put(c, new TimeSeries(c));
        }
        
        double previousEmission = 0;
        for (int i = 0; i < cacheData.size(); i++){

            String[] data = cacheData.get(i).split(",");

            double temp = previousEmission;

            try{
                previousEmission = Double.parseDouble(data[3]);
            }
            catch(Exception e){
                System.out.println("Wrong number format!");
            }

            int year = Integer.parseInt(data[2]);
        
            if (year > 1902 && countries.contains(data[0])){
                TimeSeries tm = (TimeSeries ) mapB.get(data[0]); 
                tm.add(new Day(1, 1 ,year), previousEmission - temp);
            }
        }
    
        
        for (Object s : mapB.values()){
                dataB.addSeries((TimeSeries) s);
            }
            
        reducedMapB = (HashMap)mapB.clone();
    }

    /**
     * A method for selecting a date interval in LineChartA.
     */
    public void selectDateA(int start, int end){
        dataA.removeAllSeries();
        for (Object tm : reducedMapA.values()){
            TimeSeries ts= (TimeSeries) tm;

            try{
                ts = ts.createCopy(start, end);
            }catch(Exception e){
                System.out.println("Selection error!");
            }
            dataA.addSeries(ts);
        }
    }
    
    /**
     * A method for selecting a date interval in LineChartB.
     */
    public void selectDateB(int start, int end){
        dataB.removeAllSeries();
        for (Object tm : reducedMapB.values()){
            TimeSeries ts= (TimeSeries) tm;

            try{
                ts = ts.createCopy(start, end);
            }catch(Exception e){
                System.out.println("Selection error!");
            }
            dataB.addSeries(ts);
        }
    }

    /**
     * A method for selecting the regions to display in LineChartA.
     * @param regions - the user-selected regions to display
      */
    public void selectRegionsA(ArrayList<String> regions){
        dataA.removeAllSeries();
        if (regions.contains("All")){
            for (Object tm : mapA.values()){
                dataA.addSeries((TimeSeries) tm);
            }
            reducedMapA = (HashMap)mapA.clone();
        }
        else{
            reducedMapA.clear();
            for(String reg : regions){
                TimeSeries tm = (TimeSeries) mapA.get(reg);
                dataA.addSeries(tm);
                reducedMapA.put(reg, tm);
            }
        }
    }
    
    /**
     * A method for selecting the regions to display in LineChartB.
     * @param regions - the user-selected regions to display
      */
    public void selectRegionsB(ArrayList<String> regions){
        dataB.removeAllSeries();
        if (regions.contains("All")){
            for (Object tm : mapB.values()){
                dataB.addSeries((TimeSeries) tm);
            }
            reducedMapB = (HashMap)mapB.clone();
        }
        else{
            reducedMapB.clear();
            for(String reg : regions){
                TimeSeries tm = (TimeSeries) mapB.get(reg);
                dataB.addSeries(tm);
                reducedMapB.put(reg, tm);
            }
        }
    }

    /**
     * A method for intializing the bar chart
     * @param end - end date of the selected time interval
     * @param start - start date of the selected time interval
     * 
      */
    public void barData(int start, int end){
        //avg growth rate over time= (present/past)^(1/N) - 1
        barData.clear();
        double temp = 0;

        for(String region : countries){
            int N = 0;
            for(int i = 1; i < cacheData.size(); i++){
                String[] data = cacheData.get(i).split(",");

                double emissions = 0; // co2 value
                
                try{
                    emissions = Double.parseDouble(data[3]);
                }catch(Exception e){
                    System.out.println("Wrong number format!");
                }

                int year = Integer.parseInt(data[2]);

                if(emissions != 0 && year == start && region.equals(data[0]))
                    temp = emissions;
                
                else if(emissions != 0 && year == end && region.equals(data[0]))
                    temp = emissions/temp;
                
                else if(emissions != 0 && year > start && year < end && region.equals(data[0]))
                    N++;
            }

            String interval = "" + start + "-" + end;
            double n = 1.0/N;
            double percentage = (Math.pow(temp, n) - 1);
            
            barData.addValue(percentage*100, region, interval);
            N = 1;
        }
    }

    /**
     * A method for intializing the pie chart
     * @param year - the currently selected year
     * 
      */
    public void pieData(int year){
        for(int i = 1; i < cacheData.size(); i++){
            String[] data = cacheData.get(i).split(",");
            
            double emissions = 0; // co2 value
            try{
                emissions = Double.parseDouble(data[3]);
            }catch(Exception e){
                System.out.println("wrong number format");
            }

            int yr = Integer.parseInt(data[2]);
            
            if( yr == year && countries.contains(data[0])){
                pieData.setValue(data[0], emissions);
            }
        }

    }
    
}
