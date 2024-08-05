package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

public class TaskFactory {

    private static TaskFactory INSTANCE;

    private final ZoneId systemZoneId;

    private TaskFactory(ZoneId systemZoneId) {
        this.systemZoneId = systemZoneId;
    }

    public static TaskFactory getInstance(ZoneId systemZoneId) {
        if(INSTANCE == null) {
            INSTANCE = new TaskFactory(systemZoneId);
        }
        return INSTANCE;
    }

    public List<Task> createTasks(List<Automation> automations) {
        List<Task> tasks = new ArrayList<>();
        if(!automations.isEmpty()) {
            for(Automation automation: automations) {
                tasks.addAll(this.createTasks(automation));
            }
        }
        return tasks;
    }

    public List<Task> createTasks(Automation automation) {
        LocalTime powerOnTime = LocalTimeUtil.convertToSystemTime(automation.getPowerOnTime(), automation.getZoneId(), this.systemZoneId);
        LocalTime powerOffTime = LocalTimeUtil.convertToSystemTime(automation.getPowerOffTime(), automation.getZoneId(), this.systemZoneId);
        return List.of(
            new TaskPowerOn(powerOnTime),
            new TaskPowerOff(powerOffTime)
        );
    }
    
}
