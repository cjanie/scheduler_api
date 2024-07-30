package com.cjanie.scheduler_api;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;

@Service
public class TickService {

    private Schedule schedule;

    private long delay = 0l;

    public TickService() {
        // Prepare
        this.schedule = new Schedule();
        Task task1 = new TaskPowerOn(LocalTime.of(20, 0, 0));
        Task task2 = new TaskPowerOff(LocalTime.of(8, 0, 0));
        this.schedule.setTasks(List.of(task1, task2));
    }


    public long getDelay() {
        this.delay += 1000;
        return this.delay;
    }

    public void tick() {
        LocalTime now = LocalTime.now();
        System.out.println("schedule tasks with dynamic delay - " + now);
        List<Task> tasks = this.schedule.filterTasksByTriggerTime(now);
        for (Task task : tasks) {
            task.run();
        }

        
    }

}
