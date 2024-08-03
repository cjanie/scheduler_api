package com.cjanie.scheduler_api.businesslogic.services;

import java.time.LocalTime;

public class TickServiceState implements GetTickTime {

    private LocalTime tickTime;

    @Override
    public LocalTime getTickTime() {
        return this.tickTime;
    }

    public void setTickTime(LocalTime tickTime) {
        this.tickTime = tickTime;
    }

    
}
