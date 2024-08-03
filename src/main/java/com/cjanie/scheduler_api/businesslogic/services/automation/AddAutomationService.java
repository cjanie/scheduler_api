package com.cjanie.scheduler_api.businesslogic.services.automation;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemZoneProvider;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private TaskRepository taskRepository;
    private TaskFactory taskFactory;

    public AddAutomationService(
        AutomationRepository automationRepository, 
        TaskRepository taskRepository, 
        SystemZoneProvider systemZoneProvider
        ) {
        this.automationRepository = automationRepository;
        this.taskRepository = taskRepository;
        this.taskFactory = TaskFactory.getInstance(systemZoneProvider);
    }

    public long add(Automation automation) throws RepositoryException {
        this.taskRepository.addTasks(this.taskFactory.createTasks(automation));
        return this.automationRepository.add(automation);
    }

}
