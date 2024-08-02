package com.cjanie.scheduler_api.businesslogic.services.automation;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;

public class AddAutomationService {

    private AutomationRepository automationRepository;

    public AddAutomationService(AutomationRepository automationRepository) {
        this.automationRepository = automationRepository;
    }

    public long add(Automation automation) throws RepositoryException {
        return this.automationRepository.add(automation);
    }

}
