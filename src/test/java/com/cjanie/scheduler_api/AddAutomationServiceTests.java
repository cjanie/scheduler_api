package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.services.TickServiceState;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

public class AddAutomationServiceTests {
    
    @Test
    public void addAutomationWithSuccess() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        TaskRepository taskRepository = new InMemoryTaskRepository();
        SystemTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(0, 0, 0));
        AddAutomationService addAutomationService = new AddAutomationService(
            automationRepository, 
            taskRepository, 
            timeProvider,
            new TickServiceState(),
            new InMemoryRunTaskAPI(timeProvider)
            );
        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), timeProvider.getZoneId());
        long result = addAutomationService.add(automation);
        assertNotEquals(0, result);

        assertEquals(2, taskRepository.getTasks().size());
        
        Task powerOn = taskRepository.getTasks().get(0);
        assertTrue(powerOn instanceof TaskPowerOn);
        assertEquals(1, powerOn.getTriggerTime().getHour());

        Task powerOff = taskRepository.getTasks().get(1);
        assertTrue(powerOff instanceof TaskPowerOff);
        assertEquals(2, powerOff.getTriggerTime().getHour());
    }

}
