package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class Automation {

    private LocalTime powerOnTime;

    private LocalTime powerOffTime;

    private ZoneId zoneId;

    public Automation(LocalTime powerOnTime, LocalTime powerOffTime, ZoneId zoneId) {
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


    public ZoneId getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }


}
