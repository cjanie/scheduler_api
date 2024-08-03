package com.cjanie.scheduler_api.adapters.primary.controllers;

import java.time.LocalTime;
import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AutomationDTO {

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime powerOnTime;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime powerOffTime;

    private String zoneId;

    public AutomationDTO(LocalTime powerOnTime, LocalTime powerOffTime, String zoneId) {
        this.powerOnTime = powerOnTime;
        this.powerOffTime = powerOffTime;
        this.zoneId = zoneId;
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
                this.powerOnTime,
                this.powerOffTime,
                ZoneId.of(this.zoneId)
                );
        return automation;
    }

}
