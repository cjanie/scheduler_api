package com.cjanie.scheduler_api.adapters.secondary;

import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.gateways.ZoneProvider;

public class SystemDefaultZoneProvider implements ZoneProvider {

    @Override
    public ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }
    
}
