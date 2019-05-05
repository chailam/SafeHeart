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

    public AbstractDataRetrieval(){
        patientMap = new HashMap<>();
        choObsMap = new HashMap<>();
    }
    // getters
    
    public HashMap <String,Patient> getPatientMap(){
        return this.patientMap;
    }
    
    public HashMap <String, Observation> getChoObsMap(){
        return this.choObsMap;
    }

}
