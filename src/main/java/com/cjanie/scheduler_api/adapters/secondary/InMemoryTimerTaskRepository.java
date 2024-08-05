package com.cjanie.scheduler_api.adapters.secondary;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import com.cjanie.scheduler_api.businesslogic.IdentifiedTimerTask;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;

public class InMemoryTimerTaskRepository implements TimerTaskRepository {

    private Map<IdentifiedTimerTask, Timer> timerTasks;

    public InMemoryTimerTaskRepository() {
        this.timerTasks = new HashMap<>();
    }

    @Override
    public long add(IdentifiedTimerTask timerTask, Timer timer) {
        this.timerTasks.put(timerTask, timer);
        return timerTasks.size();
    }

    @Override
    public Map<IdentifiedTimerTask, Timer> getTimerTasks() {
        return this.timerTasks;
    }

}
