/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controller.CholesterolMonitor;
import controller.Controller;
import controller.InterfaceMonitor;


public class Client {
    public static void main(String args[]){
        //Retrieve data from server
        DataRetrieval dR = new DataRetrieval();
        dR.startRetrieve();

        Controller controller;
        controller = new Controller(dR.getPersonList(),dR.getObList());
        controller.startView();
        
        InterfaceMonitor m;
        m = new CholesterolMonitor();
        
        System.out.println(dR.reGetObs());
        /*TODO: set time loop to execute this every 1 hours*/
        //m.monitorUpdate(dR, controller);
     }
}
