package com.cjanie.scheduler_api.adapters.secondary;

import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;

public class InMemoryRunTaskAPI implements RunTaskGateway {

    private static String TAG = InMemoryRunTaskAPI.class.getName(); 
    @Override
    public void runTaskPowerOn() {
        System.out.println(TAG + " run task power on ()");
    }

    @Override
    public void runTaskPowerOff() {
        System.out.println(TAG + " run task power off ()");
    }
    
}
