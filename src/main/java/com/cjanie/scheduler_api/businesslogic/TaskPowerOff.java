package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

public class TaskPowerOff extends Task {

    private static final String TAG = TaskPowerOff.class.getName(); 

    public TaskPowerOff(LocalTime triggerTime) {
        super(triggerTime);
    }

    @Override
    public void run() {
        System.out.println(TAG + " run()");
    }
    
}
