/*
 * Client that runs the server. The Client class that contains the main function
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


public class Client {
    /**
     * Main function of the program
     * @param args command line arguments
     */
    public static void main(String args[]){
        // initialise controller in MVC design
        Controller c;
        c = new Controller();
        c.initView();
        
        
        // set time loop to execute every 1 hours on the monitor to update observation
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                 c.update();
            }
        }, 0, 1, TimeUnit.HOURS);
     }
}
