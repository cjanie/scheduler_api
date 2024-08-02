package com.cjanie.scheduler_api.adapters.primary.controllers;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AutomationDTO {

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime powerOnTime;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime powerOffTime;

    public AutomationDTO(LocalTime powerOnTime, LocalTime powerOffTime) {
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

    public Automation createAutomation() {
        Automation automation = new Automation(
                this.getPowerOnTime(),
                this.getPowerOffTime()
                );
        return automation;
    }

}
