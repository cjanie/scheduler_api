package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.Set;

import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class TaskPowerOff extends Task {

    public TaskPowerOff(Set<Device> devices, LocalTime triggerTime) {
        super(devices, triggerTime);
    }

    @Override
    public void run(RunTaskGateway runTaskGateway) {
        runTaskGateway.runTaskPowerOff(this);
    }
    
}
