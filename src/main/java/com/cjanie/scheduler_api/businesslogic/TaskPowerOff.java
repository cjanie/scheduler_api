package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

public class TaskPowerOff extends Task {

    public TaskPowerOff(LocalTime triggerTime) {
        super(triggerTime);
    }

    @Override
    public void runSpecificCommand() {
        this.runTaskGateway.runTaskPowerOff();
    }
    
}
