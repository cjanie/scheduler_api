package com.cjanie.scheduler_api.adapters;

import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;

public class InMemoryAutomationRepository implements AutomationRepository {

    private List<Automation> automations;


    public InMemoryAutomationRepository() {
        this.automations = new ArrayList<Automation>();
    }

    @Override
    public long add(Automation automation) throws RepositoryException {
        this.automations.add(automation);
        return this.automations.size();
    }

    public List<Automation> getAutomations() {
        return this.automations;
    }
}
