package com.cjanie.scheduler_api.businesslogic.gateways;

import java.time.ZoneId;

public interface ZoneProvider {

    ZoneId getZoneId();
    
}
