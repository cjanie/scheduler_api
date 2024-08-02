package com.cjanie.scheduler_api.businesslogic.services.automation;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private TaskRepository taskRepository;

    public AddAutomationService(AutomationRepository automationRepository, TaskRepository taskRepository) {
        this.automationRepository = automationRepository;
        this.taskRepository = taskRepository;
    }

    public long add(Automation automation) throws RepositoryException {
        this.taskRepository.addTasks(automation.createTasks());
        return this.automationRepository.add(automation);
    }

}
