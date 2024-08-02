package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.TimeProvider;

public class RealTimeProvider implements TimeProvider {

    @Override
    public LocalTime now() {
        return LocalTime.now();
    }
    
}
