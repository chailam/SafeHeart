/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.system;

import java.util.ArrayList;
import java.util.HashMap;
import xd.safeheart.model.*;


/**
 * Abstract Data Retrieval
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

    public Observation getRecentObsByPat(Patient p, String string);

    public ArrayList<ArrayList<Observation>> getAllHistoricObsByPat(Patient p, String string);

    public void reGetObs();
    
}
