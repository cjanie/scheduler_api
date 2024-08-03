package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public abstract class Task {
    
    private LocalTime triggerTime;

    public Task(LocalTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public LocalTime getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(LocalTime runTime) {
        this.triggerTime = runTime;
    }

    public abstract void run(RunTaskGateway runTaskGateway) throws GatewayException;
}
