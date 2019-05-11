/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import my.model.Ob;
import my.model.Person;

public class TableModel2 extends AbstractTableModel {

    private ArrayList <ArrayList<Ob>> diasBlood;
    private ArrayList <ArrayList<Ob>> sysBlood;
    private ArrayList <Ob> tobacOb;
    private String[] columnNames = { "FamilyName", "GivenName", "Graph",
                "Tobacco", "Unit","Alert"};
    private int columnLength = 6;
    private int rowLength;

    public TableModel2(ArrayList <ArrayList<Ob>> diasBlood, ArrayList <ArrayList<Ob>> sysBlood, ArrayList <Ob> tobacOb){
         this.sysBlood = sysBlood;
         this.diasBlood = diasBlood;
         this.tobacOb = tobacOb;
         this.rowLength = diasBlood.size();
    }

    @Override
    public String getColumnName(int columnId){
         return columnNames[columnId];
    }

    @Override     
    public int getRowCount() {
        return this.rowLength;
    }

    @Override        
    public int getColumnCount() {
        return columnLength; 
    }
   
    
    private boolean testAlert(ArrayList<Ob>sysBlood,  ArrayList<Ob>diasBlood){
        boolean alert = false;
        for (Ob o : sysBlood){
            if (Integer.parseInt(o.getValue()) > 180){
                alert = true;
            }
        }
        for (Ob o : diasBlood){
            if (Integer.parseInt(o.getValue()) > 120){
                alert = true;
            }
        }
        return alert;
    }

    @Override
    public Object getValueAt(int rowId, int columnId) {
        ArrayList<Ob> sysBL = sysBlood.get(rowId);
        ArrayList<Ob> diaBL = diasBlood.get(rowId);
        Ob o = this.tobacOb.get(rowId);
        Person p = o.getPatient();
        LineChart chart = new LineChart(p.getId()+p.getFamilyName()+p.getGivenName(),diaBL,sysBL);
        chart.setSize(800, 400);
        chart.setVisible(true);
        switch (columnId) {
            case 0: 
                return p.getFamilyName();
            case 1:
                return p.getGivenName();
            case 2:
//                LineChart chart = new LineChart(p.getId()+p.getFamilyName()+p.getGivenName(),diaBL,sysBL);
//                chart.setVisible(true);
                return "aa";
            case 3:
                return o.getValue();
            case 4:
                return o.getUnit();
            case 5:
                return testAlert(sysBL,diaBL);
           }
           return null;
   }

   @Override
   public Class<?> getColumnClass(int columnId){
          switch (columnId){
             case 0:
               return String.class;
             case 1:
               return String.class;
             case 2:
               return LineChart.class;
             case 3:
               return String.class;
             case 4:
               return String.class;
             case 5:
               return Boolean.class;
             }
             return null;
      }
 }
