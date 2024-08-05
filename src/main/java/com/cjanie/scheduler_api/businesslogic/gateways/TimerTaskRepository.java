package com.cjanie.scheduler_api.businesslogic.gateways;

import java.util.Map;
import java.util.Timer;

import com.cjanie.scheduler_api.businesslogic.IdentifiedTimerTask;

public interface TimerTaskRepository {

    long add(IdentifiedTimerTask timerTask, Timer timer);
    Map<IdentifiedTimerTask, Timer> getTimerTasks();

}
