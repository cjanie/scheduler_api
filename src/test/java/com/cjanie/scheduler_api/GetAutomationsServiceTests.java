package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.services.automation.GetAutomationsService;

public class GetAutomationsServiceTests {
    
    @Test
    public void noAutomations() throws RepositoryException {
        List<Automation> automations = new GetAutomationsService(new InMemoryAutomationRepository()).getAutomations();
        assertEquals(0, automations.size());
    }

    @Test
    public void automations() throws RepositoryException {
        InMemoryAutomationRepository automationRepository = new InMemoryAutomationRepository();
        automationRepository.setAutomations(List.of(
            new Automation(LocalTime.now(), LocalTime.now().plusHours(1), ZoneId.of("UTC")),
            new Automation(LocalTime.now(), LocalTime.now().plusHours(1), ZoneId.of("UTC"))
        ));

        List<Automation> automations = new GetAutomationsService(automationRepository).getAutomations();
        assertEquals(2, automations.size());
    }
}
