package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;

public class ScheduleTests {
    
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


    @Test
    public void nextTask() {
        // Prepare
        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();
        Task task0 = new TaskPowerOn(LocalTime.of(20, 0, 0));
        Task task1 = new TaskPowerOn(LocalTime.of(8, 0, 0));
        Task task2 = new TaskPowerOn(LocalTime.of(10, 0, 0));
        Task task3 = new TaskPowerOff(LocalTime.of(9, 0, 0));
        List<Task> tasks = List.of(task0, task1, task2, task3);
        taskRepository.setTasks(tasks);
        // SUT
        Schedule schedule = new Schedule(taskRepository);
        LocalTime next = schedule.getNextTriggerTime(LocalTime.of(10, 0, 0));
        assertEquals(20, next.getHour());
    }
}
