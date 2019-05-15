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
package xd.safeheart.view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import xd.safeheart.model.Observation;
import xd.safeheart.model.Patient;



public class TableModel2 extends AbstractTableModel {

    private ArrayList <Observation> tobacOb;
    private String[] columnNames = { "FamilyName", "GivenName", "Age",
                "Gender", "Tobacco", "Unit"};
    private int columnLength = 6;
    private int rowLength;
    private boolean first = true;

    public TableModel2(ArrayList <Observation> tobacOb){
         this.tobacOb = tobacOb;
         this.rowLength = tobacOb.size();
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

    @Override
    public Object getValueAt(int rowId, int columnId) {
        
        Observation o = this.tobacOb.get(rowId);
        Patient p = o.getPatient();
        switch (columnId) {
            case 0: 
                return p.getFamilyName();
            case 1:
                return p.getGivenName();
            case 2:
                return p.getAge();
            case 3:
                return p.getGender();
            case 4:
                return o.getValue();
            case 5:
                return o.getUnit();
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
               return Integer.class;
             case 3:
               return String.class;
             case 4:
               return String.class;
             case 5:
               return String.class;
             }
             return null;
      }
 }
