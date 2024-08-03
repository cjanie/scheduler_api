package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        System.out.println(TAG + " - At UTC " + toUTC(this.systemTimeProvider.now()) + " : run task power on () sheduled at UTC " + this.toUTC(task.getTriggerTime()));
    }

    @Override
    public void runTaskPowerOff(TaskPowerOff task) {
        System.out.println(TAG + " - At UTC " + toUTC(this.systemTimeProvider.now()) + " : run task power off () sheduled at UTC " + this.toUTC(task.getTriggerTime()));
    
    }

    private LocalTime toUTC(LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(this.systemTimeProvider.today(), localTime); 
        ZonedDateTime zonedDateTime = localDateTime.atZone(this.systemTimeProvider.getZoneId());
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utc.toLocalTime();
    }
    
}
