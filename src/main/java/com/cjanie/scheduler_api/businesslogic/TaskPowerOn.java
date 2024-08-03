package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class TaskPowerOn extends Task {

    public TaskPowerOn(LocalTime triggerTime) {
        super(triggerTime);
    }

    @Override
    public void run(RunTaskGateway runTaskGateway) {
        runTaskGateway.runTaskPowerOn();
    }

}
