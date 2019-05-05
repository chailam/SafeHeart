/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.system;

import xd.safeheart.controller.*;
import java.util.ArrayList;
import xd.safeheart.model.*;

/**
 *
 * @author caila
 */
public class CholesterolMonitor implements InterfaceMonitor {
    
    public CholesterolMonitor(){ 
    }
    
    @Override
     public void monitorUpdate(Controller c){
         //retrieve cholesterol in DataRetrieval retrieve cholesterol function
         c.getDR().reGetObs();
         c.updateCholesterol();
     }
    
}
