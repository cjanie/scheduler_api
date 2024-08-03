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

    private TickServiceState tickServiceState;

    private RunTaskGateway runTaskGateway;

    public static final long DEFAULT_DELAY_MILLIS = 1000l;

    public TickService(
        TaskRepository taskRepository, 
        SystemTimeProvider systemTimeProvider, 
        RunTaskGateway runTaskGateway,
        TickServiceState tickServiceState
        ) {
        this.schedule = new Schedule(taskRepository);

        this.systemTimeProvider = systemTimeProvider;

        this.tickServiceState = tickServiceState;
        LocalTime tickTime = this.schedule.getNextTriggerTime(this.systemTimeProvider.now());
        this.tickServiceState.setTickTime(tickTime);

        this.runTaskGateway = runTaskGateway;
    }

    public List<Task> tick() throws GatewayException {
        
        System.out.println("LOG " + TAG + "tick()" + " schedule tasks with dynamic delay ");

        if(this.tickServiceState.getTickTime() != null) {
            List<Task> tasks = this.schedule.filterTasksByTriggerTime(this.tickServiceState.getTickTime());
            if(!tasks.isEmpty()) {
                for (Task task : tasks) {
                    task.run(this.runTaskGateway);
                }
            } 
            this.tickServiceState.setTickTime(this.schedule.getNextTriggerTime(this.tickServiceState.getTickTime()));
            return tasks;
        } else {
            this.tickServiceState.setTickTime(this.schedule.getNextTriggerTime(this.systemTimeProvider.now()));
            return new ArrayList<>();
        }
    }

    public LocalTime getNexTickTime(LocalTime actual) {
        return this.schedule.getNextTriggerTime(actual);
    }

    public LocalTime getNextTickTime() {
        if(this.tickServiceState.getTickTime() != null) {
            return this.tickServiceState.getTickTime();
        }
        return this.schedule.getNextTriggerTime(this.systemTimeProvider.now());
    }

    public long getDefaultDelayMillis() {
        return DEFAULT_DELAY_MILLIS;
    }

}
