package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalTime;

import com.cjanie.scheduler_api.businesslogic.gateways.TimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.ZoneProvider;

public class RealTimeProvider implements TimeProvider {

    private ZoneProvider zoneProvider;


    public RealTimeProvider(ZoneProvider zoneProvider) {
        this.zoneProvider = zoneProvider;
    }


    @Override
    public LocalTime now() {
        return LocalTime.now(this.zoneProvider.getZoneId());
    }
    
}
