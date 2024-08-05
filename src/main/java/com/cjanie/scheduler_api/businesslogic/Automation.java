package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.time.ZoneId;

public class Automation {

    private Long id;

    private LocalTime powerOnTime;

    private LocalTime powerOffTime;

    private ZoneId zoneId;

    public Automation(LocalTime powerOnTime, LocalTime powerOffTime, ZoneId zoneId) {
        this.powerOnTime = powerOnTime;
        this.powerOffTime = powerOffTime;
        this.zoneId = zoneId;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public ZoneId getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }


}
