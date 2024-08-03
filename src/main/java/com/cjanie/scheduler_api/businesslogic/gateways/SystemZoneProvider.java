package com.cjanie.scheduler_api.businesslogic.gateways;

import java.time.ZoneId;

public interface SystemZoneProvider {

    ZoneId getZoneId();
    
}
