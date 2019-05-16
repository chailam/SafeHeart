package xd.safeheart.view;

import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import xd.safeheart.model.Observation;

public class LineChart extends JFrame {

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
      setContentPane(chartPanel);
   }
   
   public ChartPanel returnchartPanel(){
       return this.chartPanel;
   }

   private XYDataset createDataset( ) {
      TimeSeriesCollection series = new TimeSeriesCollection();
      TimeSeries sysDateSet = new TimeSeries("Systolic");
      for (Observation o : sysObs){
          sysDateSet.add(new Day(o.getDay(),o.getMonth(),o.getYear()),Float.parseFloat(o.getValue()));
      }
      
      TimeSeries diasDataSet = new TimeSeries("Diastolic");
      System.out.println(sysObs);
      System.out.println(diasObs);
      for (Observation o : diasObs){
          diasDataSet.add(new Day(o.getDay(),o.getMonth(),o.getYear()),Float.parseFloat(o.getValue()));
      }
      
      series.addSeries(diasDataSet);
      series.addSeries(sysDateSet);
      return series;
   }
}