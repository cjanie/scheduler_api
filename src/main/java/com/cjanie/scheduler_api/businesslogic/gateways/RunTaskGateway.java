package com.cjanie.scheduler_api.businesslogic.gateways;

import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;

public interface RunTaskGateway {
    void runTaskPowerOn(TaskPowerOn task);
    void runTaskPowerOff(TaskPowerOff task);
}
