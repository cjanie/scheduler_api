package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.adapters.secondary.RealTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.SystemDefaultZoneProvider;
import com.cjanie.scheduler_api.adapters.secondary.UTCZoneProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemZoneProvider;
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

    private SystemTimeProvider systemTimeProvider;

    private SystemZoneProvider systemZoneProvider;

    private AutomationRepository automationRepository;

    private RunTaskGateway runTaskGateway;

    private TickService tickService;

    private AddAutomationService addAutomationService;

    private TaskRepository taskRepository() {
        if (this.taskRepository == null) {
            this.taskRepository = new InMemoryTaskRepository();
        }
        return this.taskRepository;
    }

    public SystemTimeProvider systemTimeProvider() {
        if(this.systemTimeProvider == null) {
            this.systemTimeProvider = new RealTimeProvider(this.systemZoneProvider());
        }
        return this.systemTimeProvider;
    }

    private SystemZoneProvider systemZoneProvider() {
        if(this.systemZoneProvider == null) {
            this.systemZoneProvider = new SystemDefaultZoneProvider();
        }
        return this.systemZoneProvider;
    }

    private AutomationRepository automationRepository() {
        if(this.automationRepository == null) {
            this.automationRepository = new InMemoryAutomationRepository();
        }
        return this.automationRepository;
    }

    private RunTaskGateway runTaskGateway() {
        if(this.runTaskGateway == null) {
            this.runTaskGateway = new InMemoryRunTaskAPI(this.systemTimeProvider());
        }
        return this.runTaskGateway;
    }

    public TickService tickService() {
        if(this.tickService == null) {
            this.tickService = new TickService(
                this.taskRepository(), 
                this.systemTimeProvider(), 
                this.runTaskGateway());
        }
        return this.tickService;
    }

    public AddAutomationService addAutomationService() {
        if(this.addAutomationService == null) {
            this.addAutomationService = new AddAutomationService(
                this.automationRepository(),
                this.taskRepository(),
                this.systemZoneProvider()
            );
        }
        return this.addAutomationService;
    }

    
}
