package com.cjanie.scheduler_api;

import com.cjanie.scheduler_api.adapters.secondary.InMemoryAutomationRepository;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryRunTaskAPI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTimerTaskRepository;
import com.cjanie.scheduler_api.adapters.secondary.RealTimeProvider;
import com.cjanie.scheduler_api.adapters.secondary.SystemDefaultZoneProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemZoneProvider;
import com.cjanie.scheduler_api.businesslogic.services.automation.AddAutomationService;


public class DI {

    private static DI INSTANCE;

    private SystemZoneProvider systemZoneProvider;

    private SystemTimeProvider systemTimeProvider;

    private AutomationRepository automationRepository;

    private TimerTaskRepository timerTaskRepository;

    private RunTaskGateway runTaskGateway;

    private AddAutomationService addAutomationService;

    private DI() {

    }

    public static DI getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DI();
        }
        return INSTANCE;
    }

    private SystemZoneProvider systemZoneProvider() {
        if(this.systemZoneProvider == null) {
            this.systemZoneProvider = new SystemDefaultZoneProvider();
        }
        return this.systemZoneProvider;
    }

    private SystemTimeProvider systemTimeProvider() {
        if(this.systemTimeProvider == null) {
            this.systemTimeProvider = new RealTimeProvider(this.systemZoneProvider());
        }
        return this.systemTimeProvider;
    }

    private AutomationRepository automationRepository() {
        if(this.automationRepository == null) {
            this.automationRepository = new InMemoryAutomationRepository();
        }
        return this.automationRepository;
    }

    private TimerTaskRepository timerTaskRepository() {
        if (this.timerTaskRepository == null) {
            this.timerTaskRepository = new InMemoryTimerTaskRepository();
        }
        return this.timerTaskRepository;
    }


    private RunTaskGateway runTaskGateway() {
        if(this.runTaskGateway == null) {
            this.runTaskGateway = new InMemoryRunTaskAPI(this.systemTimeProvider());
        }
        return this.runTaskGateway;
    }

    public AddAutomationService addAutomationService() {
        if(this.addAutomationService == null) {
            this.addAutomationService = new AddAutomationService(
                this.automationRepository(),
                this.systemTimeProvider(),
                this.runTaskGateway(),
                this.timerTaskRepository()
            );
        }
        return this.addAutomationService;
    }
    
}
