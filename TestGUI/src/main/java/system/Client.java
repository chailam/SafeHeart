/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import client.CholesterolMonitor;
import client.DataRetrieval;
import client.InterfaceMonitor;
import controller.Controller;
import java.util.ArrayList;
import my.model.Ob;


public class Client {
    public static void main(String args[]){
        //Retrieve data from server
        DataRetrieval dR = new DataRetrieval();
        dR.startRetrieve();

        Controller c;        
        c = new Controller(dR.getPersonList(),dR.getObList(),dR.getDiasBloodObList(),dR.getSysBloodObList(),dR.getTobacObList());
        c.startView();
        
        InterfaceMonitor m;
        m = new CholesterolMonitor();
        
        /*TODO: set time loop to execute this every 1 hours*/
        //m.monitorUpdate(dR, c);
     }
}
