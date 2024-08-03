package com.cjanie.scheduler_api.businesslogic.gateways;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public interface SystemTimeProvider {
    LocalDate today();
    LocalTime now();
    LocalDateTime nowDateTime();
    ZoneId getZoneId();
}
