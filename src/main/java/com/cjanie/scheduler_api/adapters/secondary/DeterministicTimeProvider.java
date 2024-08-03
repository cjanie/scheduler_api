package com.cjanie.scheduler_api.adapters.secondary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;

public class DeterministicTimeProvider implements SystemTimeProvider {

    private LocalDateTime now;

    public DeterministicTimeProvider(LocalTime now) {
        this.now = LocalDateTime.of(LocalDate.now(), now);
    }

    public DeterministicTimeProvider(LocalDateTime now) {
        this.now = now;
    }


    @Override
    public LocalTime now() {
        return now.toLocalTime();
    }


    @Override
    public ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }


    @Override
    public LocalDate today() {
        return this.now.toLocalDate();
    }


    @Override
    public LocalDateTime nowDateTime() {
        return this.now;
    }
    
}
