package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.GenericTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.GenericZoneProvider;

public class RealTimeProvider implements GenericTimeProvider {

    private GenericZoneProvider zoneProvider;


    public RealTimeProvider(GenericZoneProvider zoneProvider) {
        this.zoneProvider = zoneProvider;
    }


    @Override
    public LocalTime now() {
        return LocalTime.now(this.zoneProvider.getZoneId());
    }
    
}
