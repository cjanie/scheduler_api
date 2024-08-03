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
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.ZoneProvider;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private TaskRepository taskRepository;
    private ZoneProvider zoneProvider;

    public AddAutomationService(AutomationRepository automationRepository, TaskRepository taskRepository, ZoneProvider zoneProvider) {
        this.automationRepository = automationRepository;
        this.taskRepository = taskRepository;
        this.zoneProvider = zoneProvider;
    }

    public long add(Automation automation) throws RepositoryException {
        this.taskRepository.addTasks(this.createTasks(automation));
        return this.automationRepository.add(automation);
    }

    public List<Task> createTasks(Automation automation) {
        return List.of(
            new TaskPowerOn(this.getRefTime(automation.getPowerOnTime(), automation.getZoneId())),
            new TaskPowerOff(this.getRefTime(automation.getPowerOffTime(), automation.getZoneId()))
        );
    }

    private LocalTime getRefTime(LocalTime localTime, ZoneId zoneId) {
        LocalDate today = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(today, localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneId);
        ZonedDateTime refDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return refDateTime.toLocalTime();
    }

}
