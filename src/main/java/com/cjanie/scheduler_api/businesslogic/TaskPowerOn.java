package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

public class TaskPowerOn extends Task {

    public TaskPowerOn(LocalTime triggerTime) {
        super(triggerTime);
    }

    @Override
    public void runSpecificCommand() {
        this.runTaskGateway.runTaskPowerOn();
    }

}
