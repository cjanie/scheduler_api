package com.cjanie.scheduler_api.businesslogic.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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

    public static Duration getDuration(LocalTime start, LocalTime end) {
        Duration duration;
            if(end.isBefore(start)) {
                LocalDate today = LocalDate.now();
                LocalDateTime actualDateTime = LocalDateTime.of(today, start);
                LocalDateTime nextDateTime = LocalDateTime.of(today.plusDays(1), end); 
                duration = Duration.between(actualDateTime, nextDateTime);
            } else {
                duration = Duration.between(start, end);
            }
            return duration;
    }

    public static LocalTime convertInstantToLocalTime(Instant instant) {
        return LocalDateTimeUtil.convertInstantToLocalDateTime(instant).toLocalTime();
    }

    public static Instant convertLocalTimeToInstant(LocalTime time) {
        LocalTime now = LocalTime.now();
        LocalDateTime dateTime;
        LocalDate today = LocalDate.now();
        if(time.isBefore(now)) {
            dateTime = LocalDateTime.of(today.plusDays(1), time);
        } else {
            dateTime = LocalDateTime.of(today, time);
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

}
