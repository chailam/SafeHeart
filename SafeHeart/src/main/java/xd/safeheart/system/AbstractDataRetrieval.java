/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.system;

import java.util.ArrayList;
import xd.safeheart.model.*;



public abstract class AbstractDataRetrieval {
    //Interface or Abstract???
    protected ArrayList <Patient> lPatient;
    protected ArrayList <Observation> lOb;

    public AbstractDataRetrieval(){
        lPatient = new ArrayList<>();
        lOb = new ArrayList<>();
}
    
    public ArrayList <Patient> getPersonList(){
        return this.lPatient;
    }
    
    public ArrayList <Observation> getObList(){
        return this.lOb;
    }

   /* TODO: all the setter and getter*/

}
