package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.gateways.GenericZoneProvider;

public class TaskFactory {

    private static TaskFactory INSTANCE;

    private final ZoneId genericZoneId;

    private TaskFactory(GenericZoneProvider globalZoneProvider) {
        this.genericZoneId = globalZoneProvider.getZoneId();
    }

    public static TaskFactory getInstance(GenericZoneProvider genericZoneProvider) {
        if(INSTANCE == null) {
            INSTANCE = new TaskFactory(genericZoneProvider);
        }
        return INSTANCE;
    }

    public List<Task> createTasks(Automation automation) {
        LocalTime powerOnGenericTime = this.convertToGenericZoneTime(automation.getPowerOnTime(), automation.getZoneId());
        LocalTime powerOffGenericTime = this.convertToGenericZoneTime(automation.getPowerOffTime(), automation.getZoneId());
        return List.of(
            new TaskPowerOn(powerOnGenericTime),
            new TaskPowerOff(powerOffGenericTime)
        );
    }

    private LocalTime convertToGenericZoneTime(LocalTime localTime, ZoneId zoneId) {
        LocalDate today = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(today, localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneId);
        ZonedDateTime refDateTime = zonedDateTime.withZoneSameInstant(this.genericZoneId);
        return refDateTime.toLocalTime();
    }
    
}
