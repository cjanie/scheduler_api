package com.cjanie.scheduler_api.adapters.primary.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjanie.scheduler_api.DI;
import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;
import com.cjanie.scheduler_api.businesslogic.services.automation.GetAutomationsService;
import com.cjanie.scheduler_api.businesslogic.services.timertask.ScheduleTimerTaskService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/automations")
public class AutomationController {

    private AddAutomationService addAutomationService;

    private ScheduleTimerTaskService scheduleTimerTaskService;

    private GetAutomationsService getAutomationsService;

    public AutomationController() {
        DI di = DI.getInstance();
        this.addAutomationService = di.addAutomationService();
        this.scheduleTimerTaskService = di.scheduleTimerTaskService();
        this.getAutomationsService = di.getAutomationsService();
    }
    

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody AutomationDTO automationDTO) {
        Automation automation = automationDTO.createAutomation();
        try {
            long automationId = this.addAutomationService.add(automation);
            
            automation.setId(automationId);
            this.scheduleTimerTaskService.sheduleTimer(automation);

            return new ResponseEntity<Long>(automationId, HttpStatus.OK);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            return new ResponseEntity<List<Automation>>(this.getAutomationsService.getAutomations(), HttpStatus.OK);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
    
}
