package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTimerTaskRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Timer;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;
import com.cjanie.scheduler_api.businesslogic.services.automation.IdentifiedTimerTask;

public class AddAutomationServiceTests {

    private SystemTimeProvider timeProvider = new DeterministicTimeProvider(
        LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 0, 0))
        );
    
    @Test
    public void addAutomationWithSuccess() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        TimerTaskRepository timerTaskRepository = new InMemoryTimerTaskRepository();

        AddAutomationService addAutomationService = new AddAutomationService(
            automationRepository,
            this.timeProvider,
            new InMemoryRunTaskAPI(this.timeProvider),
            timerTaskRepository
            );        
        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), ZoneId.of("UTC"));

        long automationResult = addAutomationService.add(automation);
        assertNotEquals(0, automationResult);

    }

    @Test
    public void scheduleAutomationWithSuccess() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        TimerTaskRepository timerTaskRepository = new InMemoryTimerTaskRepository();

        AddAutomationService addAutomationService = new AddAutomationService(
            automationRepository,
            this.timeProvider,
            new InMemoryRunTaskAPI(this.timeProvider),
            timerTaskRepository
            );        
        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), ZoneId.of("UTC"));

        // SUT
        addAutomationService.add(automation);
        
        Map<IdentifiedTimerTask, Timer> timerTasksMappedByTime = timerTaskRepository.getTimerTasks();
        assertEquals(2, timerTasksMappedByTime.keySet().size());
    }

    @Test
    public void oneAutomationShouldHaveOneTimerTaskForTasksOfTheSameTime() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        TimerTaskRepository timerTaskRepository = new InMemoryTimerTaskRepository();

        AddAutomationService addAutomationService = new AddAutomationService(
            automationRepository,
            this.timeProvider,
            new InMemoryRunTaskAPI(this.timeProvider),
            timerTaskRepository
            );        
        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(1, 0, 0), ZoneId.of("UTC"));

        // SUT
        addAutomationService.add(automation);
        
        Map<IdentifiedTimerTask, Timer> timerTasksMappedByTime = timerTaskRepository.getTimerTasks();
        assertEquals(1, timerTasksMappedByTime.keySet().size());
    }

}
