package com.cjanie.scheduler_api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TimeProvider;

public class TickService {

    private static final String TAG = TickService.class.getName();

    private Schedule schedule;

    private TimeProvider timeProvider;

    private LocalTime tickTime;

    public static final long DEFAULT_DELAY_MILLIS = 1000l;
    

    public TickService(TaskRepository taskRepository, TimeProvider timeProvider) {
        this.schedule = new Schedule(taskRepository);
        this.timeProvider = timeProvider;
        this.tickTime = this.schedule.getNextTriggerTime(this.timeProvider.now());
    }

    public List<Task> tick() {
        
        System.out.println("LOG " + TAG + "tick()" + " schedule tasks with dynamic delay ");

        if(this.tickTime != null) {
            List<Task> tasks = this.schedule.filterTasksByTriggerTime(this.tickTime);
            if(!tasks.isEmpty()) {
                for (Task task : tasks) {
                    task.run();
                }
            } 
            this.tickTime = this.schedule.getNextTriggerTime(this.tickTime);
            return tasks;
        } else {
            this.tickTime = this.schedule.getNextTriggerTime(this.timeProvider.now());
            return new ArrayList<>();
        }
    }

    public LocalTime getNexTickTime(LocalTime actual) {
        return this.schedule.getNextTriggerTime(actual);
    }

    public LocalTime getNextTickTime() {
        if(this.tickTime != null) {
            return this.tickTime;
        }
        return this.schedule.getNextTriggerTime(this.timeProvider.now());
    }


    public void setTickTime(LocalTime tickTime) {
        this.tickTime = tickTime;
    }

    public long getDefaultDelayMillis() {
        return DEFAULT_DELAY_MILLIS;
    }

}
