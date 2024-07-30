package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

public class TaskPowerOn extends Task {

    private static final String TAG = TaskPowerOn.class.getName();

    public TaskPowerOn(LocalTime triggerTime) {
        super(triggerTime);
    }

    @Override
    public void run() {
        System.out.println(TAG + " run()");
    }

}
