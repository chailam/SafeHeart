/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.system;

import xd.safeheart.controller.*;
import java.util.HashMap;
import xd.safeheart.model.*;

/**
 *
 * @author caila
 */
public class CholesterolMonitor implements InterfaceMonitor {
    
    public CholesterolMonitor(){ 
    }
    
    @Override
     public void monitorUpdate(DataRetrieval dR, Controller c){
         //retrieve cholesterol in DataRetrieval retrieve cholesterol function
//         HashMap <String, Observation> newlObd = dR.reGetObs();
//         c.updateCholesterol(newlObd);       
     }
    
}
