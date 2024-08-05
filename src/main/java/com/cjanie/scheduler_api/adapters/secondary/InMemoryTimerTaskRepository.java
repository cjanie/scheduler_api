package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;

public class InMemoryTimerTaskRepository implements TimerTaskRepository {

    private Map<LocalTime, TimerTask> timerTaskMappedByTime;


    public InMemoryTimerTaskRepository() {
        this.timerTaskMappedByTime = new HashMap<>();
    }


    @Override
    public long add(LocalTime time, TimerTask timerTask) {
        this.timerTaskMappedByTime.put(time, timerTask);
        return timerTaskMappedByTime.size();
    }


    @Override
    public Map<LocalTime, TimerTask> getTimerTasksMappedByTime() {
        return this.timerTaskMappedByTime;
    }
    
}
