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

        Controller c;
        c = new Controller();
        c.initView();
        
        InterfaceMonitor m;
        m = new CholesterolMonitor();
        
        /*TODO: set time loop to execute this every 1 hours*/
        //m.monitorUpdate(dR, c);
     }
}
