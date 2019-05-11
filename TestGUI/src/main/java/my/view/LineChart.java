package my.view;

import java.util.ArrayList;
import my.model.Ob;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class LineChart extends ApplicationFrame {

   private ChartPanel chartPanel;
   private ArrayList<Ob> sysObs;
   private ArrayList<Ob> diasObs;
   public LineChart( String chartTitle, ArrayList<Ob> sysObs, ArrayList<Ob> diasObs ) {
      super(chartTitle);
     this.sysObs = sysObs;
     this.diasObs = diasObs;
      JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
         chartTitle,
         "Time","Blood Pressure",
         createDataset(),
         true,true,false);
         
      chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 100 , 100 ) );
   }
   
   public ChartPanel returnchartPanel(){
       return this.chartPanel;
   }

   private XYDataset createDataset( ) {
      TimeSeriesCollection dataset = new TimeSeriesCollection();
      TimeSeries dataset1 = new TimeSeries("Systolic");
      for (Ob o : sysObs){
      
          dataset1.add(new Day(o.getDay(),o.getMonth(),o.getYear()),Integer.parseInt(o.getValue()));
      }
//      dataset1.add( new Day(1, 1, 1970), 15 );
//      dataset1.add( new Day(1, 1, 1980), 30 );
//      dataset1.add( new Day(1, 1, 1990), 60  );
//      dataset1.add( new Day(1, 1, 2000),120  );
//      dataset1.add( new Day(1, 1, 2010),240);
//      dataset1.add( new Day(1, 1, 2020),300 );
      
      TimeSeries dataset2 = new TimeSeries("Diastolic");
      for (Ob o : diasObs){
      
          dataset1.add(new Day(o.getDay(),o.getMonth(),o.getYear()),Integer.parseInt(o.getValue()));
      }
//      dataset2.add( new Day(1, 1, 1970), 300 );
//      dataset2.add(  new Day(1, 1, 1980), 240 );
//      dataset2.add( new Day(1, 1, 1990), 120  );
//      dataset2.add( new Day(1, 1, 2000),60  );
//      dataset2.add( new Day(1, 1, 2010),30);
//      dataset2.add( new Day(1, 1, 2020),15 );
      
      dataset.addSeries(dataset2);
      dataset.addSeries(dataset1);
      return dataset;
   }
}