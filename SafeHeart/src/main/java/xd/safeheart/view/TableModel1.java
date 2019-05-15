/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import xd.safeheart.model.*;

/**
 * It renders the Table model which determines its style, and columns
 * @author Chai Lam
 * @author Aik Han
 */
public class TableModel1 extends AbstractTableModel {

    private ArrayList <Observation> selectedO;
    private String[] columnNames = { "FamilyName", "GivenName", "Age",
                "Gender", "Cholesterol", "Unit"};
    private int columnLength = 6;
    private int rowLength;

    public TableModel1(ArrayList<Observation> selectedO){
         this.selectedO = selectedO;
         this.rowLength = selectedO.size();
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
        //Patient p = selectedP.get(rowId);
        Observation o = selectedO.get(rowId);
        switch (columnId) {
            case 0: 
                return o.getPatient().getFamilyName();
            case 1:
                return o.getPatient().getGivenName();
            case 2:
                return o.getPatient().getAge();
            case 3:
                return o.getPatient().getGender();
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
