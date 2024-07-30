package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.InMemoryTaskRepository;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;

public class ScheduleTest {
    
    @Test
    public void shouldReturnTasksToRunAtASpecificTime() {

         // Prepare schedule with tasks
        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();
        Task task1 = new TaskPowerOn(LocalTime.of(20, 0, 0));
        Task task2 = new TaskPowerOff(LocalTime.of(8, 0, 0));
        taskRepository.setTasks(List.of(task1, task2));
        Schedule schedule = new Schedule(taskRepository);

        // SUT
        List<Task> tasks = schedule.filterTasksByTriggerTime(LocalTime.of(8, 0, 0));

        assertEquals(1, tasks.size());
    }

    @Test
    public void shouldReturnNoTaskWhenThereIsNoneToRunAtASpecificTime() {

         // Prepare empty schedule (tasks empty)
        Schedule schedule = new Schedule(new InMemoryTaskRepository());

        // SUT
        List<Task> tasks = schedule.filterTasksByTriggerTime(LocalTime.of(8, 0, 0));

        assertEquals(0, tasks.size());
    }
}
