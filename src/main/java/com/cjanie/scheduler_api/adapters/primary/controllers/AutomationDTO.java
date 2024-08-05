package com.cjanie.scheduler_api.adapters.primary.controllers;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Device;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AutomationDTO {

    private Set<String> devicesAddresses;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime powerOnTime;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime powerOffTime;

    private String zoneId;

    public AutomationDTO(Set<String> devicesAddresses, LocalTime powerOnTime, LocalTime powerOffTime, String zoneId) {
        this.devicesAddresses = devicesAddresses;
        this.powerOnTime = powerOnTime;
        this.powerOffTime = powerOffTime;
        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(String zoneId) {
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
        Set<Device> devices = new HashSet<>();
        for(String address : this.devicesAddresses) {
            devices.add(new Device(address));
        }
        Automation automation = new Automation(
            devices,
            this.powerOnTime,
            this.powerOffTime,
            ZoneId.of(this.zoneId)
        );
        return automation;
    }

}
