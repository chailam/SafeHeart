package my.view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class LineChart_AWT extends ApplicationFrame {

    private ChartPanel chartPanel;
   public LineChart_AWT( String chartTitle ) {
      super(chartTitle);
      JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
         chartTitle,
         "Years","Number of Schools",
         createDataset(),
         true,true,false);
         
      chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 100 , 100 ) );
      //setContentPane( chartPanel );
   }
   
   public ChartPanel returnchartPanel(){
       return this.chartPanel;
   }

   private XYDataset createDataset( ) {
      TimeSeriesCollection dataset = new TimeSeriesCollection();
      TimeSeries dataset1 = new TimeSeries("Systolic");
      dataset1.add( new Day(1, 1, 1970), 15 );
      dataset1.add( new Day(1, 1, 1980), 30 );
      dataset1.add( new Day(1, 1, 1990), 60  );
      dataset1.add( new Day(1, 1, 2000),120  );
      dataset1.add( new Day(1, 1, 2010),240);
      dataset1.add( new Day(1, 1, 2020),300 );
      
      TimeSeries dataset2 = new TimeSeries("Diastolic");
      dataset2.add( new Day(1, 1, 1970), 300 );
      dataset2.add(  new Day(1, 1, 1980), 240 );
      dataset2.add( new Day(1, 1, 1990), 120  );
      dataset2.add( new Day(1, 1, 2000),60  );
      dataset2.add( new Day(1, 1, 2010),30);
      dataset2.add( new Day(1, 1, 2020),15 );
      
      dataset.addSeries(dataset2);
      dataset.addSeries(dataset1);
      return dataset;
   }
   
//   public static void main( String[ ] args ) {
//      LineChart_AWT chart = new LineChart_AWT(
//         "School Vs Years" ,
//         "Numer of Schools vs years");
//
//      chart.pack( );
//      RefineryUtilities.centerFrameOnScreen( chart );
//      chart.setVisible( true );
//   }
}