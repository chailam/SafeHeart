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

public class TableModel extends AbstractTableModel {

    private ArrayList <Person> selectedP;
    private ArrayList <Ob> selectedO;
    private String[] columnNames = { "FamilyName", "GivenName", "Age",
                "Gender", "Cholesterol", "Unit"};
    private int columnLength = 6;
    private int rowLength;

    public TableModel(ArrayList<Person> selectedP, ArrayList<Ob> selectedO){
         this.selectedO = selectedO;
         this.selectedP = selectedP;
         this.rowLength = selectedP.size();
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
        Person p = selectedP.get(rowId);
        Ob o = selectedO.get(rowId);
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
