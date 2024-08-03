package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.secondary.DeterministicTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.services.TickService;

public class TickServiceTests {

    @Test
    public void runsNothingWhenNoTaskAvailable() throws GatewayException {
        // Prépare
        LocalTime now = LocalTime.of(1, 0, 0);
        TickService tickService = new TickService(
            new InMemoryTaskRepository(),
            new DeterministicTimeProvider(now),
            new InMemoryRunTaskAPI()
            );
        assertEquals(0, tickService.tick().size());
    }
    
    @Test
    public void runsTasksScheduledOnTickTime() throws GatewayException {
        // Prépare
        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();
        Task task1 = new TaskPowerOn(LocalTime.of(0, 26, 0));
        Task task2 = new TaskPowerOff(LocalTime.of(0, 26, 0));
        Task task3 = new TaskPowerOff(LocalTime.of(0, 21, 0));
        taskRepository.setTasks(List.of(task1, task2, task3));

        LocalTime now = LocalTime.of(0, 25, 0);

        TickService tickService = new TickService(
            taskRepository,
            new DeterministicTimeProvider(now),
            new InMemoryRunTaskAPI()
            );
        assertEquals(2, tickService.tick().size());
        assertEquals(1, tickService.tick().size());
        assertEquals(2, tickService.tick().size());
    }
}
