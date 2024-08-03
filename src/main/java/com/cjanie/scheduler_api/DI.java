package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.adapters.secondary.RealTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.SystemDefaultZoneProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.GenericTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.GenericZoneProvider;
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

    private GenericTimeProvider timeProvider;

    private GenericZoneProvider zoneProvider;

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

    private GenericTimeProvider genericTimeProvider() {
        if(this.timeProvider == null) {
            this.timeProvider = new RealTimeProvider(this.genericZoneProvider());
        }
        return this.timeProvider;
    }

    private GenericZoneProvider genericZoneProvider() {
        if(this.zoneProvider == null) {
            this.zoneProvider = new SystemDefaultZoneProvider();
        }
        return this.zoneProvider;
    }

    private AutomationRepository automationRepository() {
        if(this.automationRepository == null) {
            this.automationRepository = new InMemoryAutomationRepository();
        }
        return this.automationRepository;
    }

    private RunTaskGateway runTaskGateway() {
        if(this.runTaskGateway == null) {
            this.runTaskGateway = new InMemoryRunTaskAPI();
        }
        return this.runTaskGateway;
    }

    public TickService tickService() {
        if(this.tickService == null) {
            this.tickService = new TickService(
                this.taskRepository(), 
                this.genericTimeProvider(), 
                this.runTaskGateway());
        }
        return this.tickService;
    }

    public AddAutomationService addAutomationService() {
        if(this.addAutomationService == null) {
            this.addAutomationService = new AddAutomationService(
                this.automationRepository(),
                this.taskRepository(),
                this.genericZoneProvider()
            );
        }
        return this.addAutomationService;
    }

    
}
