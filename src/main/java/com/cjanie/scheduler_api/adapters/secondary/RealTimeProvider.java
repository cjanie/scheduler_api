package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemZoneProvider;

public class RealTimeProvider implements SystemTimeProvider {

    private SystemZoneProvider zoneProvider;


    public RealTimeProvider(SystemZoneProvider zoneProvider) {
        this.zoneProvider = zoneProvider;
    }


    @Override
    public LocalTime now() {
        return LocalTime.now(this.getZoneId());
    }
    

    @Override
    public ZoneId getZoneId() {
        return this.zoneProvider.getZoneId();
    }

    @Override
    public LocalDate today() {
        return LocalDate.now(this.getZoneId());
    }

    @Override
    public LocalDateTime nowDateTime() {
        return LocalDateTime.now(this.getZoneId());
    }
}
