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
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

public class AddAutomationServiceTests {
    
    @Test
    public void addAutomationWithSuccess() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        AddAutomationService addAutomationService = new AddAutomationService(
            automationRepository
            );        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), ZoneId.of("UTC"));

        long result = addAutomationService.add(automation);
        assertNotEquals(0, result);
    }

}
