package xd.safeheart.view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import xd.safeheart.model.Observation;
import xd.safeheart.model.Patient;


/**
 * For tobacco
 * It renders the Table model which determines its style, and columns
 * @author Chai Lam
 * @author Aik Han
 */
public class TobaccoTableModel extends AbstractTableModel {

    private final ArrayList <Observation> tobacOb;
    private final String[] columnNames = { "ObsId", "PatientId", "FamilyName", "GivenName", "Age",
                "Gender", "TobaccoStatus"};
    private final int columnLength = 7;
    private final int rowLength;

    public TobaccoTableModel(ArrayList <Observation> tobacOb){
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
