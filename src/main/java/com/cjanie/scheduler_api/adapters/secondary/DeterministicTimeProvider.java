package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.GenericTimeProvider;

public class DeterministicTimeProvider implements GenericTimeProvider {

    private LocalTime now;


    public DeterministicTimeProvider(LocalTime now) {
        this.now = now;
    }


    @Override
    public LocalTime now() {
        return now;
    }
    
}
