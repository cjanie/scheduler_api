package com.cjanie.scheduler_api;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApiApplication.class, args);
	}

}
