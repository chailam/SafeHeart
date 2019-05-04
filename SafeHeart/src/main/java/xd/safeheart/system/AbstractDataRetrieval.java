/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.system;

import java.util.HashMap;
import xd.safeheart.model.*;



public abstract class AbstractDataRetrieval {
    //Interface or Abstract???
    protected HashMap <String, Patient> patientMap;
    protected HashMap <String, Observation> choObsMap;

    public AbstractDataRetrieval(){
        patientMap = new HashMap<>();
        choObsMap = new HashMap<>();
}
    
    public HashMap <String,Patient> getPatientMap(){
        return this.patientMap;
    }
    
    public HashMap <String, Observation> getChoObsMap(){
        return this.choObsMap;
    }

   /* TODO: all the setter and getter*/

}
