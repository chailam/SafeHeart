/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.system;

import xd.safeheart.controller.*;

/**
 * It notifies the data to update and the view to update
 * @author Chai Lam
 * @author Aik Han
 */
public class CholesterolMonitor implements InterfaceMonitor {
    
    public CholesterolMonitor(){ 
    }
    
    /**
     * Updates all cholesterol
     * @param c
     */
    @Override
     public void monitorUpdate(Controller c){
         //retrieve cholesterol in DataRetrieval retrieve cholesterol function
         c.getDR().reGetObs();
         c.updateObservations();
     }
    
}
