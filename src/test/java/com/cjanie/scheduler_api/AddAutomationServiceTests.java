package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.primary.TasksTimer;
import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

public class AddAutomationServiceTests {

    private SystemTimeProvider timeProvider = new DeterministicTimeProvider(
        LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 0, 0))
        );
    
    @Test
    public void addAutomationWithSuccess() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        AddAutomationService addAutomationService = new AddAutomationService(
            automationRepository,
            this.timeProvider,
            new TasksTimer(this.timeProvider),
            new InMemoryRunTaskAPI(this.timeProvider),
            new Schedule(automationRepository, this.timeProvider)

            );        
        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), ZoneId.of("UTC"));

        long result = addAutomationService.add(automation);
        assertNotEquals(0, result);
    }

}
