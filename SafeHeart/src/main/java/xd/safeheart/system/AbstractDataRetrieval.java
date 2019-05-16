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
public abstract class AbstractDataRetrieval {
    protected HashMap <String, Patient> patientMap;
    protected HashMap <String, Encounter> encounterMap;
    protected HashMap <String, Observation> choObsMap;
    protected HashMap <String, ArrayList<Observation>> bloodDiasObsMap;
    protected HashMap <String, ArrayList<Observation>> bloodSysObsMap;
    protected HashMap <String, Observation> tobacObsMap;

    public AbstractDataRetrieval(){
        patientMap = new HashMap<>();
        encounterMap = new HashMap<>();
        choObsMap = new HashMap<>();
        bloodDiasObsMap = new HashMap<>();
        bloodSysObsMap = new HashMap<>();
        tobacObsMap = new HashMap<>();
    }
    // getters
    
    public HashMap <String,Patient> getPatientMap(){
        return this.patientMap;
    }
    
    public HashMap<String, Observation> getChoObsMap() {
        return choObsMap;
    }

    public HashMap<String, ArrayList<Observation>> getBloodDiasObsMap() {
        return bloodDiasObsMap;
    }

    public HashMap<String, ArrayList<Observation>> getBloodSysObsMap() {
        return bloodSysObsMap;
    }

    public HashMap<String, Observation> getTobacObsMap() {
        return tobacObsMap;
    }
}
