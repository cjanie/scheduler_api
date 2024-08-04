package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimerTask;

import com.cjanie.scheduler_api.DI;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class DynamicTimerTask extends TimerTask {

    private Schedule schedule = DI.getInstance().schedule();

    private LocalTime runTime;

    private RunTaskGateway runTaskGateway;


    public DynamicTimerTask(Schedule schedule, LocalTime runTime, RunTaskGateway runTaskGateway) {
        this.schedule = schedule;
        this.runTime = runTime;
        this.runTaskGateway = runTaskGateway;
    }

    @Override
    public void run() {
        List<Task> tasks;
        try {
            tasks = this.schedule.filterTasksByTriggerTime(runTime);
            for (Task task: tasks) {
                task.run(this.runTaskGateway);
            }
        } catch (GatewayException e) {
            e.printStackTrace();
        }
    }
}
