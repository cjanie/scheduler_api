package com.cjanie.scheduler_api.adapters.secondary;

import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemZoneProvider;

public class UTCZoneProvider implements SystemZoneProvider {

    @Override
    public ZoneId getZoneId() {
        return ZoneId.of("UTC");
    }
    
}
