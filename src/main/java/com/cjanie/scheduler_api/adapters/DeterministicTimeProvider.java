package com.cjanie.scheduler_api.adapters;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.TimeProvider;

public class DeterministicTimeProvider implements TimeProvider {

    private LocalTime now;


    public DeterministicTimeProvider(LocalTime now) {
        this.now = now;
    }


    @Override
    public LocalTime now() {
        return now;
    }
    
}
