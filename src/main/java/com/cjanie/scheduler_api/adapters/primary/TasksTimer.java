package com.cjanie.scheduler_api.adapters.primary;

import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerGateway;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

public class TasksTimer implements TimerGateway {

    private SystemTimeProvider systemTimeProvider;

    private Timer timer;

    public TasksTimer(SystemTimeProvider systemTimeProvider) {
        this.systemTimeProvider = systemTimeProvider;
        this.timer = new Timer();
    }

    @Override
    public void schedule(TimerTask task, LocalTime time) {
        Date date = LocalTimeUtil.timeToDate(time, this.systemTimeProvider);
        this.timer.schedule(task, date);
    }}
