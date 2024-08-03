package com.cjanie.scheduler_api.businesslogic.gateways;

import java.time.LocalTime;

public interface GenericTimeProvider {
    LocalTime now();
}
