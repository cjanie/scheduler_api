package com.cjanie.scheduler_api.adapters.primary.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjanie.scheduler_api.DI;
import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/automations")
public class AutomationController {

    private AddAutomationService addAutomationService;

    public AutomationController() {
        this.addAutomationService = DI.getInstance().addAutomationService();
    }
    

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody AutomationDTO automationDTO) {
        Automation automation = automationDTO.createAutomation();
        try {
            long result = this.addAutomationService.add(automation);
            return new ResponseEntity<Long>(result, HttpStatus.OK);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
