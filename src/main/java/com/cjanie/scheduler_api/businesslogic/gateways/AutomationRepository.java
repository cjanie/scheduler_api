package com.cjanie.scheduler_api.businesslogic.gateways;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;

public interface AutomationRepository {

    long add(Automation automation) throws RepositoryException;

}
