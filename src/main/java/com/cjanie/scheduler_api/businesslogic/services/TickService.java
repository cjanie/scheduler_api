package com.cjanie.scheduler_api.businesslogic.services;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;

public class TickService {

    private static final String TAG = TickService.class.getName();

    private Schedule schedule;

    private SystemTimeProvider systemTimeProvider;

    private LocalTime tickTime;

    private RunTaskGateway runTaskGateway;

    public static final long DEFAULT_DELAY_MILLIS = 1000l;

    public TickService(TaskRepository taskRepository, SystemTimeProvider systemTimeProvider, RunTaskGateway runTaskGateway) {
        this.schedule = new Schedule(taskRepository);
        this.systemTimeProvider = systemTimeProvider;
        this.tickTime = this.schedule.getNextTriggerTime(this.systemTimeProvider.now());

        this.runTaskGateway = runTaskGateway;
    }

    public List<Task> tick() throws GatewayException {
        
        System.out.println("LOG " + TAG + "tick()" + " schedule tasks with dynamic delay ");

        if(this.tickTime != null) {
            List<Task> tasks = this.schedule.filterTasksByTriggerTime(this.tickTime);
            if(!tasks.isEmpty()) {
                for (Task task : tasks) {
                    task.run(this.runTaskGateway);
                }
            } 
            this.tickTime = this.schedule.getNextTriggerTime(this.tickTime);
            return tasks;
        } else {
            this.tickTime = this.schedule.getNextTriggerTime(this.systemTimeProvider.now());
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
        return this.schedule.getNextTriggerTime(this.systemTimeProvider.now());
    }

    public long getDefaultDelayMillis() {
        return DEFAULT_DELAY_MILLIS;
    }

}
