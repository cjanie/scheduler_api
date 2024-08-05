package com.cjanie.scheduler_api.businesslogic.gateways;

import java.time.LocalTime;
import java.util.Map;
import java.util.TimerTask;

public interface TimerTaskRepository {

    long add(LocalTime time, TimerTask timerTask);
    Map<LocalTime, TimerTask> getTimerTasksMappedByTime();

}
