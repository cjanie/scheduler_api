package com.cjanie.scheduler_api.businesslogic.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;

public class LocalTimeUtil {


    public static LocalDateTime timeToDateTime(LocalTime time, SystemTimeProvider systemTimeProvider) {
        LocalDateTime now = systemTimeProvider.nowDateTime();
        if(time.isBefore(now.toLocalTime())) {
            return LocalDateTime.of(now.plusDays(1).toLocalDate(), time);
        } else {
            return LocalDateTime.of(now.toLocalDate(), time);
        }
    }

    public static Date timeToDate(LocalTime time, SystemTimeProvider systemTimeProvider) {
        LocalDateTime localDateTime = LocalTimeUtil.timeToDateTime(time, systemTimeProvider);
        return Date.from(localDateTime.atZone(systemTimeProvider.getZoneId()).toInstant());
    }

    public static LocalTime convertToSystemTime(LocalTime localTime, ZoneId zoneId, ZoneId systemZoneId) {
        LocalDate today = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(today, localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneId);
        ZonedDateTime refDateTime = zonedDateTime.withZoneSameInstant(systemZoneId);
        return refDateTime.toLocalTime();
    }

    public static boolean isTheSameTime(LocalTime time, LocalTime otherTime) {
        return time.getHour() == otherTime.getHour() 
            && time.getMinute() == otherTime.getMinute() 
            && time.getSecond() == otherTime.getSecond();
    }

}
