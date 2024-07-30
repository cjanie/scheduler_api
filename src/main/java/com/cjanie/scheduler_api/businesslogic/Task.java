package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

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

    abstract public void run();

}
