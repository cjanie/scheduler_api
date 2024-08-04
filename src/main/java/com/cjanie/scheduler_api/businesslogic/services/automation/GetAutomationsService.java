package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;

public class GetAutomationsService {
    
    private AutomationRepository automationRepository;

    public GetAutomationsService(AutomationRepository automationRepository) {
        this.automationRepository = automationRepository;
    }

    public List<Automation> getAutomations() throws RepositoryException {
        return this.automationRepository.get();
    }

}
