package com.cjanie.scheduler_api;

import java.time.LocalTime;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;

public class TickService {

    private Schedule schedule;

    private long delay = 0l;

    public TickService(TaskRepository taskRepository) {
        this.schedule = new Schedule(taskRepository);
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
