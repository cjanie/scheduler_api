package com.cjanie.scheduler_api.adapters.secondary;

import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemZoneProvider;

public class SystemDefaultZoneProvider implements SystemZoneProvider {

    @Override
    public ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }
    
}
