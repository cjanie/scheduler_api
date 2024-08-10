package com.cjanie.scheduler_api.businesslogic.services.automation;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;

class UpdateAutomationService(private val automationRepository: AutomationRepository) {

    fun updateAutomation(automation: Automation) : Long {
        return automationRepository.update(automation);
    }
}