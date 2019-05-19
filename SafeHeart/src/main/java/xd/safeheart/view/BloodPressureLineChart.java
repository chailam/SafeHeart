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

/**
 * For Blood pressure (diastolic & systolic) Observation
 * It renders the Line Chart model which determines its style, and data
 * @author Chai Lam Loi (28136179)
 */
public class BloodPressureLineChart extends JFrame {

   private final ChartPanel chartPanel;
   private final ArrayList<Observation> sysObs;
   private final ArrayList<Observation> diasObs;
   
   // constructor
   public BloodPressureLineChart(String title, String chartTitle, ArrayList<Observation> sysObs, ArrayList<Observation> diasObs ) {
        super(title);
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
   
   // returns chart panel
   public ChartPanel returnchartPanel(){
        return this.chartPanel;
   }

   // creates dataset of xy axis
   private XYDataset createDataset( ) {
        TimeSeriesCollection series = new TimeSeriesCollection();
        TimeSeries sysDateSet = new TimeSeries("Systolic");
        for (Observation o : sysObs){
            sysDateSet.addOrUpdate(new Day(o.getDay(),o.getMonth(),o.getYear()),Float.parseFloat(o.getValue()));
        }

        TimeSeries diasDataSet = new TimeSeries("Diastolic");
        for (Observation o : diasObs){
            diasDataSet.addOrUpdate(new Day(o.getDay(),o.getMonth(),o.getYear()),Float.parseFloat(o.getValue()));
        }

        series.addSeries(diasDataSet);
        series.addSeries(sysDateSet);
        return series;
     }
}