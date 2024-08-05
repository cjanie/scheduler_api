package com.cjanie.scheduler_api;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.services.automation.GetAutomationsService;
import com.cjanie.scheduler_api.businesslogic.services.timertask.ScheduleTimerTaskService;

@SpringBootApplication
public class SchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApiApplication.class, args);

		// In case the Automation database infrastructure already exists, 
		// timer tasks should be initialized
		init();
	}

	private static void init() {
		DI di = DI.getInstance();
		GetAutomationsService getAutomationsService = di.getAutomationsService();
		ScheduleTimerTaskService scheduleTimerTaskService = di.scheduleTimerTaskService();

		try {
			List<Automation> automations = getAutomationsService.getAutomations();
			if(!automations.isEmpty()) {
				for(Automation automation: automations) {
					scheduleTimerTaskService.sheduleTimer(automation);
				}
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

}
