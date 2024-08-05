package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class UTCTests {
    
    @Test
    public void utc() {
        // samedi à 03:00 à Tokyo, Japon correspond à
        // vendredi à 18:00 dans le fuseau horaire Coordinated Universal Time (UTC)
        LocalTime localTime = LocalTime.of(3, 0, 0);
        LocalDate localDate = LocalDate.of(2024, 12, 23);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalTime utc = utcZonedDateTime.toLocalTime();
        assertEquals(3, zonedDateTime.getHour());
        assertEquals(18, utcZonedDateTime.getHour());
        assertEquals(18, utc.getHour());
    }
}
