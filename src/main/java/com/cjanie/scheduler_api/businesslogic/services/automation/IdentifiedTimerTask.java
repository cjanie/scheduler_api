package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.util.List;
import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class IdentifiedTimerTask extends TimerTask {

    private long automationId;

    private List<Task> tasks;

    private RunTaskGateway runTaskGateway;

    public IdentifiedTimerTask(long automationId, List<Task> tasks, RunTaskGateway runTaskGateway) {
        this.automationId = automationId;
        this.tasks = tasks;
        this.runTaskGateway = runTaskGateway;
    }

    public long getAutomationId() {
        return this.automationId;
    }

    @Override
    public void run() {
        try {
            
            for (Task task: this.tasks) {
                task.run(this.runTaskGateway);
            }
        } catch (GatewayException e) {
            // TODO handle the task that have failed
            e.printStackTrace();
        }
    }

}
