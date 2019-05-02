/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import client.DataRetrieval;
import java.util.ArrayList;
import my.model.Ob;

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
         ArrayList <Ob> newlObd = dR.reGetObs();
         c.updateChoMonitor(newlObd);       
     }
    
}
