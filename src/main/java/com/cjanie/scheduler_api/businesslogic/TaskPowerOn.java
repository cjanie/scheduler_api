package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.Set;

import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class TaskPowerOn extends Task {

    public TaskPowerOn(Set<Device> devices, LocalTime triggerTime) {
        super(devices, triggerTime);
    }

    @Override
    public void run(RunTaskGateway runTaskGateway) {
        runTaskGateway.runTaskPowerOn(this);
    }

}
