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
    private final String[] columnNames = { "ObsId", "PatientId", "FamilyName", "GivenName", "Age",
                "Gender", "Cholesterol", "Unit"};
    private final int columnLength = 8;
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
        Patient p = o.getPatient();
        switch (columnId) {
            case 0:
                return o.getID();
            case 1:
                return p.getId();
            case 2: 
                return p.getFamilyName();
            case 3:
                return p.getGivenName();
            case 4:
                return p.getAge();
            case 5:
                return p.getGender();
            case 6:
                return o.getValue();
            case 7:
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
             case 7:
               return String.class;
             }
             return null;
      }
 }
