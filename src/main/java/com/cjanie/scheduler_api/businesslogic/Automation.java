package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.List;

public class Automation {

    private LocalTime powerOnTime;

    private LocalTime powerOffTime;

    public Automation(LocalTime powerOnTime, LocalTime powerOffTime) {
        this.powerOnTime = powerOnTime;
        this.powerOffTime = powerOffTime;
    }

    public LocalTime getPowerOnTime() {
        return this.powerOnTime;
    }

    public void setPowerOnTime(LocalTime powerOnTime) {
        this.powerOnTime = powerOnTime;
    }

    public LocalTime getPowerOffTime() {
        return this.powerOffTime;
    }

    public void setPowerOffTime(LocalTime powerOffTime) {
        this.powerOffTime = powerOffTime;
    }

    public List<Task> createTasks() {
        return List.of(
            new TaskPowerOn(this.powerOnTime),
            new TaskPowerOff(this.powerOffTime)
        );
    }

}
