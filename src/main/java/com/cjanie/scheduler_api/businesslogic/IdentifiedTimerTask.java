package com.cjanie.scheduler_api.businesslogic;

import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class IdentifiedTimerTask extends TimerTask {

    private long automationId;

    private Task task;

    private RunTaskGateway runTaskGateway;

    public IdentifiedTimerTask(long automationId, Task task, RunTaskGateway runTaskGateway) {
        this.automationId = automationId;
        this.task = task;
        this.runTaskGateway = runTaskGateway;
    }

    public long getAutomationId() {
        return this.automationId;
    }

    @Override
    public void run() {
        try {
            task.run(this.runTaskGateway);
            
        } catch (GatewayException e) {
            // TODO handle the task that have failed
            e.printStackTrace();
        }
    }

}
