package com.cjanie.scheduler_api.businesslogic.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;

public class LocalTimeUtil {
    
    public static List<LocalTime> sortTimesAsc(List<LocalTime> times) {
        List<LocalTime> sortedTimes = new ArrayList<>();
        sortedTimes.add(times.get(0));
        for(int i=1; i < times.size(); i++) {
            for(int j = 0; j < sortedTimes.size(); j++) {
                if(!times.get(i).isBefore(sortedTimes.get(j))) {
                    if(j == sortedTimes.size() - 1) {
                        sortedTimes.add(times.get(i));
                        break;
                    } else {
                        continue;
                    }
                    
                } else {
                    sortedTimes.add(j,times.get(i));
                    break;
                }
            }
        }
        return sortedTimes;
    }

    public static LocalTime getNextTime(LocalTime actual, List<LocalTime> times) {
        if(!times.isEmpty()) {
            List<LocalTime> sortedList = LocalTimeUtil.sortTimesAsc(times);
            for(LocalTime triggerTime: sortedList) {
                if(actual.isBefore(triggerTime)) {
                    return triggerTime;
                }
            }
            return sortedList.get(0);
        }
        return null;
    }

    public static LocalTime convertInstantToLocalTime(Instant instant) {
        return LocalDateTimeUtil.convertInstantToLocalDateTime(instant).toLocalTime();
    }

    public static Instant convertLocalTimeToInstant(LocalTime time, SystemTimeProvider systemTimeProvider) {
        LocalTime now = systemTimeProvider.now();
        LocalDateTime dateTime;
        LocalDate today = systemTimeProvider.today();
        if(time.isBefore(now)) {
            dateTime = LocalDateTime.of(today.plusDays(1), time);
        } else {
            dateTime = LocalDateTime.of(today, time);
        }
        return dateTime.atZone(systemTimeProvider.getZoneId()).toInstant();
    }

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

}
