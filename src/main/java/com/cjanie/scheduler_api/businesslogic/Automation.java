package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Set;

public class Automation {

    private Long id;

    private Set<Device> devices;

    private LocalTime powerOnTime;

    private LocalTime powerOffTime;

    private ZoneId zoneId;

    public Automation(Set<Device> devices, LocalTime powerOnTime, LocalTime powerOffTime, ZoneId zoneId) {
        this.devices = devices;
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


    public Set<Device> getDevices() {
        return this.devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
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
