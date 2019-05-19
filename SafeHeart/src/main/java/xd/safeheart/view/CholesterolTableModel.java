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
 * For Total Cholesterol Observations
 * It renders the Table model which determines its style, and columns
 * @author Chai Lam
 * @author Aik Han
 */
public class CholesterolTableModel extends AbstractTableModel {

    private final ArrayList <Observation> selectedO;
    private final String[] columnNames = { "ObsId", "FamilyName", "GivenName", "Age",
                "Gender", "Cholesterol", "Unit"};
    private final int columnLength = 7;
    private final int rowLength;

    // Constructor
    public CholesterolTableModel(ArrayList<Observation> selectedO){
         this.selectedO = selectedO;
         this.rowLength = selectedO.size();
    }

    // getters for all table information
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
                return o.getID();
            case 1: 
                return o.getPatient().getFamilyName();
            case 2:
                return o.getPatient().getGivenName();
            case 3:
                return o.getPatient().getAge();
            case 4:
                return o.getPatient().getGender();
            case 5:
                return o.getValue();
            case 6:
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
               return String.class;
             case 3:
               return String.class;
             case 4:
               return String.class;
             case 5:
               return String.class;
             case 6:
               return String.class;
             }
             return null;
      }
 }
