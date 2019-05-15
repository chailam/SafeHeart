package xd.safeheart.view;

import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import xd.safeheart.model.Observation;

public class LineChart extends ApplicationFrame {

   private ChartPanel chartPanel;
   private ArrayList<Observation> sysObs;
   private ArrayList<Observation> diasObs;
   public LineChart( String chartTitle, ArrayList<Observation> sysObs, ArrayList<Observation> diasObs ) {
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
      for (Observation o : sysObs){
      
          dataset1.add(new Day(o.getDay(),o.getMonth(),o.getYear()),Integer.parseInt(o.getValue()));
      }
      
      TimeSeries dataset2 = new TimeSeries("Diastolic");
      for (Observation o : diasObs){
      
          dataset1.add(new Day(o.getDay(),o.getMonth(),o.getYear()),Integer.parseInt(o.getValue()));
      }
      
      dataset.addSeries(dataset2);
      dataset.addSeries(dataset1);
      return dataset;
   }
}