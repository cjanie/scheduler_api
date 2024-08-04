package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;

public class ScheduleTests {

    private SystemTimeProvider timeProvider = new DeterministicTimeProvider(
        LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 0, 0))
        );
    
    @Test
    public void shouldReturnTasksToRunAtASpecificTime() throws RepositoryException {

         // Prepare schedule with tasks
        InMemoryAutomationRepository automationRepository = new InMemoryAutomationRepository();
        automationRepository.setAutomations(List.of(
            new Automation(
                LocalTime.of(20, 0, 0), 
                LocalTime.of(8, 0, 0), 
                ZoneId.systemDefault()
                )
        ));
        
        Schedule schedule = new Schedule(automationRepository, this.timeProvider);

        // SUT
        List<Task> tasks = schedule.filterTasksByTriggerTime(LocalTime.of(8, 0, 0));

        assertEquals(1, tasks.size());
    }

    @Test
    public void shouldReturnNoTaskWhenThereIsNoneToRunAtASpecificTime() throws RepositoryException {

         // Prepare empty schedule (tasks empty)
        Schedule schedule = new Schedule(new InMemoryAutomationRepository(), this.timeProvider);

        // SUT
        List<Task> tasks = schedule.filterTasksByTriggerTime(LocalTime.of(8, 0, 0));

        assertEquals(0, tasks.size());
    }

}
