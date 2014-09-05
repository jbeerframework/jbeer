package com.jbeer.framework.server.monitor.timer;

import java.net.URISyntaxException;


public class SimpleTimerTest {

     
    public static void main(String args[]) throws URISyntaxException {
        SimpleTimer timer = new SimpleTimer(1000);
        timer.submitTask(new TimerTask() {
            
            public void run() {
               System.out.println("hello ");
            }
        });
        timer.start();
        while(true){
            System.out.println("main");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
     
}
 
 