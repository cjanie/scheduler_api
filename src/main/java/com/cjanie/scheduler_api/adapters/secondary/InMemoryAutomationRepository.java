package com.cjanie.scheduler_api.adapters.secondary;

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

    @Override
    public List<Automation> get() throws RepositoryException {
        return this.automations;
    }

    public void setAutomations(List<Automation> automations) {
        this.automations = automations;
    }
}
