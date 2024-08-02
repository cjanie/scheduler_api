package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

public class AddAutomationServiceTests {
    
    @Test
    public void addAutomationWithSuccess() throws RepositoryException {
        AutomationRepository automationRepository = new InMemoryAutomationRepository();
        AddAutomationService addAutomationService = new AddAutomationService(automationRepository);
        Automation automation = new Automation(LocalTime.now(), LocalTime.now().plusHours(1));
        long result = addAutomationService.add(automation);
        assertNotEquals(0, result);
    }

}
