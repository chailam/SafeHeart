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
    private String[] columnNames = { "ObsId", "FamilyName", "GivenName", "Age",
                "Gender", "Tobacco"};
    private int columnLength = 6;
    private int rowLength;

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
                return o.getID();
            case 1: 
                return p.getFamilyName();
            case 2:
                return p.getGivenName();
            case 3:
                return p.getAge();
            case 4:
                return p.getGender();
            case 5:
                return o.getValue();
           }
           return null;
   }

   @Override
   public Class<?> getColumnClass(int columnId){
          switch (columnId){
             case 0:
               return Integer.class;
             case 1:
               return String.class;
             case 2:
               return String.class;
             case 3:
               return Integer.class;
             case 4:
               return String.class;
             case 5:
               return String.class;
             }
             return null;
      }
 }