package com.cjanie.scheduler_api.adapters.secondary;

import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.gateways.GenericZoneProvider;

public class SystemDefaultZoneProvider implements GenericZoneProvider {

    @Override
    public ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }
    
}
