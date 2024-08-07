package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class TimeZoneTests {

    LocalTime localTime = LocalTime.of(12, 0);

    @Test
    public void timeZoneEuropeParis() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
            LocalDate.of(2024, 8, 5), 
            LocalTime.of(12, 0), 
            ZoneId.of("Europe/Paris")
            );
        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(12, systemZonedDateTime.getHour());
    }

    @Test
    public void timeZoneAfricaCairo() {
        
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(19, 0, 0), 
            ZoneId.of("Africa/Cairo")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
    }

    @Test
    public void timeZoneAmericaNewYork() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(12, 0, 0), 
            ZoneId.of("America/New_York")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
    }

    @Test
    public void timeZoneAsiaKolkata() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(21, 30, 0), 
            ZoneId.of("Asia/Kolkata")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(0, systemZonedDateTime.getMinute());
    }

    @Test
    public void timeZoneEuropeMoscow() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(19, 00, 0), 
            ZoneId.of("Europe/Moscow")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(0, systemZonedDateTime.getMinute());
    }

    @Test
    public void timeZoneAsiaHoChiMin() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(23, 30, 0), 
            ZoneId.of("Asia/Ho_Chi_Minh")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(30, systemZonedDateTime.getMinute());
    }

    @Test
    public void timeZonePacificAuckland() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(4, 30, 0), 
            ZoneId.of("Pacific/Auckland")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(30, systemZonedDateTime.getMinute());
    }

    @Test
    public void timeZoneAfricaHarare() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(18, 30, 0), 
            ZoneId.of("Africa/Harare")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(30, systemZonedDateTime.getMinute());
    }

    @Test
    public void timeZoneAustraliaSydney() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(02, 30, 0), 
            ZoneId.of("Australia/Sydney")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(30, systemZonedDateTime.getMinute());
    }

    @Test
    public void timeZoneAmericaArgentinaBuenosAires() {
        ZonedDateTime zonedDateTime = ZonedDateTime
        .of(
            LocalDate.of(2024,8, 1), 
            LocalTime.of(13, 30, 0), 
            ZoneId.of("America/Argentina/Buenos_Aires")
            );

        ZonedDateTime systemZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        assertEquals(18, systemZonedDateTime.getHour());
        assertEquals(30, systemZonedDateTime.getMinute());
    }

}
