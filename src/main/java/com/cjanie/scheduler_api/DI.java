package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.adapters.secondary.RealTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TimeProvider;
import com.cjanie.scheduler_api.businesslogic.services.TickService;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;

public class DI {

    private static DI INSTANCE;

    private DI() {

    }

    public static DI getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DI();
        }
        return INSTANCE;
    }

    private TaskRepository taskRepository;

    private TimeProvider timeProvider;

    private AutomationRepository automationRepository;

    private TickService tickService;

    private AddAutomationService addAutomationService;

    private TaskRepository taskRepository() {
        if (this.taskRepository == null) {
            this.taskRepository = new InMemoryTaskRepository();
        }
        return this.taskRepository;
    }

    private TimeProvider timeProvider() {
        if(this.timeProvider == null) {
            this.timeProvider = new RealTimeProvider();
        }
        return this.timeProvider;
    }

    private AutomationRepository automationRepository() {
        if(this.automationRepository == null) {
            this.automationRepository = new InMemoryAutomationRepository();
        }
        return this.automationRepository;
    }

    public TickService tickService() {
        if(this.tickService == null) {
            this.tickService = new TickService(this.taskRepository(), this.timeProvider());
        }
        return this.tickService;
    }

    public AddAutomationService addAutomationService() {
        if(this.addAutomationService == null) {
            this.addAutomationService = new AddAutomationService(
                this.automationRepository(),
                this.taskRepository()
            );
        }
        return this.addAutomationService;
    }

    
}
