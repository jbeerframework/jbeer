/**   
* @Title: Timer.java
* @Package com.jbeer.framework.server.monitor.timer
* @author Bieber
* @date 2014年6月19日 下午4:22:17
* @version V1.0   
*/

package com.jbeer.framework.server.monitor.timer;

import java.util.HashSet;
import java.util.Set;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Timer.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月19日 下午4:22:17
* @version V1.0
*/

public class SimpleTimer {

    private Set<TimerTask>      tasks   = new HashSet<TimerTask>();


    private long                intervalTime;

    private String              name;

    private Thread              timerThread;

    private boolean             running = false;


    private static final Log logger = LoggerUtil.generateLogger(SimpleTimer.class);
    public SimpleTimer(long intervalTime) {
        this.intervalTime = intervalTime;
        this.name = String.valueOf(System.currentTimeMillis());
        init();
    }

    public SimpleTimer(long intervalTime, String name) {
        this.intervalTime = intervalTime;
        this.name = name;
        init();
    }
    
    private void init(){
        timerThread = new Thread(new Runnable(){
            public void run() {
                while(true){
                working();
                try {
                    Thread.sleep(intervalTime);
                } catch (InterruptedException e) {
                    if(logger.isDebugEnabled()){
                    	logger.debug("failed to timer invoke", e);
                    }
                }
                }
            }
        },SimpleTimer.class.getName() + name);
        timerThread.setDaemon(true);
    }

    private synchronized void working(){
        
        for(TimerTask task:tasks){
            task.run();
        }
    }
    
    public void submitTask(TimerTask task) {
        if (!running) {
            tasks.add(task);
        }
    }

    public void start() {
        if (!running) {
            timerThread.start();
            running = true;
        }
    }
    
    public void stop(){
        if(running){
            timerThread.interrupt();
            running=false;
        }
    }
}
