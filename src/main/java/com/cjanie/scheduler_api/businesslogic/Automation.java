package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;

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

}
