package xd.safeheart.system;

import java.util.ArrayList;
import java.util.HashMap;
import xd.safeheart.model.*;


/**
 * Abstract interface for Data Retrieval
 * @author Chai Lam
 * @author Aik Han
 */
public interface AbstractDataRetrieval {
    
    public HashMap <String,Patient> getPatientMap();
    
    public HashMap<String, Observation> getChoObsMap();

    public HashMap<String, ArrayList<Observation>> getBloodDiasObsMap();
    
    public HashMap<String, ArrayList<Observation>> getBloodSysObsMap();

    public HashMap<String, Observation> getTobacObsMap() ;

    public boolean populateDataByPractitionerId(String pracId);

    public Practitioner getPractitioner();

    public Observation getRecentObsByPat(Patient p, String codeStr);

    public ArrayList<ArrayList<Observation>> getAllHistoricObsByPat(Patient p, String codeStr);

    public void reGetObs();
    
}
