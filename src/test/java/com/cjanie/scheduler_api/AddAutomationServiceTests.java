package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.primary.TasksTimer;
import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTimerTaskRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.TimerTask;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

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
            new TasksTimer(this.timeProvider),
            new InMemoryRunTaskAPI(this.timeProvider),
            new Schedule(automationRepository, this.timeProvider),
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
            new TasksTimer(this.timeProvider),
            new InMemoryRunTaskAPI(this.timeProvider),
            new Schedule(automationRepository, this.timeProvider),
            timerTaskRepository
            );        
        Automation automation = new Automation(LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), ZoneId.of("UTC"));

        // SUT
        addAutomationService.add(automation);
        
        Map<LocalTime, TimerTask> timerTasksMappedByTime = timerTaskRepository.getTimerTasksMappedByTime();
        assertEquals(2, timerTasksMappedByTime.keySet().size());
    }

}
