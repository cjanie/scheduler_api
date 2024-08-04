package com.cjanie.scheduler_api.businesslogic.gateways;

import java.time.LocalTime;
import java.util.TimerTask;

public interface TimerGateway {
    void schedule(TimerTask task, LocalTime time);
}
