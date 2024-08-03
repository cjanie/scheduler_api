package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public abstract class Task {
    
    private LocalTime triggerTime;

    protected RunTaskGateway runTaskGateway;

    public Task(LocalTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public LocalTime getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(LocalTime runTime) {
        this.triggerTime = runTime;
    }

    public void setRunTaskGateway(RunTaskGateway runTaskGateway) {
        this.runTaskGateway = runTaskGateway;
    }


    public void run() {
        if(this.runTaskGateway != null) {
            this.runSpecificCommand();
        }
    }

    protected abstract void runSpecificCommand();

}
