package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class TaskPowerOff extends Task {

    public TaskPowerOff(LocalTime triggerTime) {
        super(triggerTime);
    }

    @Override
    public void run(RunTaskGateway runTaskGateway) {
        runTaskGateway.runTaskPowerOff(this);
    }
    
}
