package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.businesslogic.Device;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.IdentifiedTimerTask;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.services.timertask.ScheduleTimerTaskService;

public class ScheduleTimerServiceTests {

    private SystemTimeProvider timeProvider = new DeterministicTimeProvider(
        LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 0, 0))
        );


    @Test
    public void scheduleAutomationWithSuccess() throws RepositoryException {
        
        TimerTaskRepository timerTaskRepository = new InMemoryTimerTaskRepository();

        ScheduleTimerTaskService scheduleTimerTaskService = new ScheduleTimerTaskService(
            this.timeProvider,
            new InMemoryRunTaskAPI(this.timeProvider),
            timerTaskRepository
            );        
        Automation automation = new Automation(new HashSet<Device>(), LocalTime.of(1, 0, 0), LocalTime.of(2, 0, 0), ZoneId.of("UTC"));
        automation.setId(1l);
        // SUT
        scheduleTimerTaskService.sheduleTimer(automation);
        
        Map<IdentifiedTimerTask, Timer> timerTasks = timerTaskRepository.getTimerTasks();
        assertEquals(2, timerTasks.keySet().size());
    }


    
}
