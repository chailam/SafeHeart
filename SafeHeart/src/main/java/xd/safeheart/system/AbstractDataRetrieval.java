/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.system;

import java.util.HashMap;
import xd.safeheart.model.*;


/**
 * Abstract Data Retrieval
 * @author Chai Lam
 * @author Aik Han
 */
public abstract class AbstractDataRetrieval {
    protected HashMap <String, Patient> patientMap;
    protected HashMap <String, Observation> choObsMap;
    protected HashMap <String, Observation> bloodDiasMap;
    protected HashMap <String, Observation> bloodSysMap;
    protected HashMap <String, Observation> tobacMap;
    protected HashMap <String, Encounter> encounterMap;
    

    public AbstractDataRetrieval(){
        patientMap = new HashMap<>();
        choObsMap = new HashMap<>();
        bloodDiasMap = new HashMap<>();
        bloodSysMap = new HashMap<>();
        tobacMap = new HashMap<>();
        encounterMap = new HashMap<>();
    }
    // getters
    
    public HashMap <String,Patient> getPatientMap(){
        return this.patientMap;
    }
    
    public HashMap <String, Observation> getChoObsMap(){
        return this.choObsMap;
    }
    
    public HashMap <String, Observation> getBloodDiasMap(){
        return this.bloodDiasMap;
    }
    
    public HashMap <String, Observation> getBloodSysMap(){
        return this.bloodSysMap;
    }
    
    public HashMap <String, Observation> getTobacMap(){
        return this.tobacMap;
    }

}
