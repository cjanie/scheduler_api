package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.cjanie.scheduler_api.businesslogic.Device;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;

public class InMemoryRunTaskAPI implements RunTaskGateway {

    private SystemTimeProvider systemTimeProvider;


    public InMemoryRunTaskAPI(SystemTimeProvider systemTimeProvider) {
        this.systemTimeProvider = systemTimeProvider;
    }

    private static String TAG = InMemoryRunTaskAPI.class.getName(); 
    @Override
    public void runTaskPowerOn(TaskPowerOn task) {
        System.out.println(
            "LOG from " + TAG 
            + " \n - At UTC " + toUTC(this.systemTimeProvider.now()) 
            + " : run power ON sheduled at UTC " + this.toUTC(task.getTriggerTime())
            + " \n - devices addresses : " + this.getDevicesAddresses(task)
            );
    }

    @Override
    public void runTaskPowerOff(TaskPowerOff task) {
        System.out.println(
            "LOG from " + TAG 
            + " \n - At UTC " + toUTC(this.systemTimeProvider.now()) 
            + " : run power OFF sheduled at UTC " + this.toUTC(task.getTriggerTime())
            + " \n - devices addresses : " + this.getDevicesAddresses(task)
            );
    }

    private String getDevicesAddresses(Task task) {
        String devicesAddresses = "";
        for (Device device: task.getDevices()) {
            devicesAddresses += device.getAddress() + ", ";
        }
        return devicesAddresses;
    }

    private LocalTime toUTC(LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(this.systemTimeProvider.today(), localTime); 
        ZonedDateTime zonedDateTime = localDateTime.atZone(this.systemTimeProvider.getZoneId());
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utc.toLocalTime();
    }
    
}
