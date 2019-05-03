/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.client;

import xd.safeheart.system.*;
import xd.safeheart.controller.Controller;


public class Client {
    public static void main(String args[]){
        //Retrieve data from server
        DataRetrieval dR = new DataRetrieval();
        dR.startRetrieve();

        Controller c;
        c = new Controller(dR.getPersonList(),dR.getObList(),dR.getPractitioner());
        c.startView();
        
        InterfaceMonitor m;
        m = new CholesterolMonitor();
        
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3/";
        FhirManager f;
        f = new FhirManager(serverBaseUrl);
        f.populateDataByPractitionerId("1");
        
        /*TODO: set time loop to execute this every 1 hours*/
        //m.monitorUpdate(dR, c);
     }
}
