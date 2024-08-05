package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.Set;

import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public abstract class Task {

    private Set<Device> devices;
    
    private LocalTime triggerTime;

    public Task(Set<Device> devices, LocalTime triggerTime) {
        this.devices = devices;
        this.triggerTime = triggerTime;
    }


    public Set<Device> getDevices() {
        return this.devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }


    public LocalTime getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(LocalTime runTime) {
        this.triggerTime = runTime;
    }

    public abstract void run(RunTaskGateway runTaskGateway) throws GatewayException;
}
