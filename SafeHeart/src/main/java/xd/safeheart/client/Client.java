/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import xd.safeheart.system.*;
import xd.safeheart.controller.Controller;

/*
 * Client that runs the server. The main function
 */
public class Client {
    public static void main(String args[]){
        // initialise controller in MVC design
        Controller c;
        c = new Controller();
        c.initView();
        
        // init monitor
        InterfaceMonitor m;
        m = new CholesterolMonitor();
        
        // set time loop to execute every 1 hours on the monitor to update observation
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                 m.monitorUpdate(c);
            }
        }, 0, 1, TimeUnit.HOURS);
     }
}
