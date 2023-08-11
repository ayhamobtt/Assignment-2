/*
 * This is a singleton EJB with start up annotation,
 * Runs game logic that calls advance one frame method to update the positions of bouncers
 * Author: Jordan Kent
 * Date: June 23, 2023
 */
package cst8218.kent0037.bouncer.business;

import cst8218.kent0037.bouncer.entity.Bouncer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class BouncerGame {
    
    @EJB
    private cst8218.kent0037.bouncer.business.BouncerFacade bouncerFacade;
    private static final double CHANGE_RATE = 3.0;
    private List<Bouncer> bouncers;
    
    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
 
            @Override
            public void run() {
                // the game runs indefinitely
                while (true) {
                    //update all the bouncers and save changes to the database
                    bouncers = bouncerFacade.findAll();
                    for (Bouncer bouncer : bouncers) {
                        bouncer.advanceOneFrame();
                        bouncerFacade.edit(bouncer);
                    }
                    //sleep while waiting to process the next frame of the animation
                    try {
                        // wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long)(1.0/CHANGE_RATE*1000));                               
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
