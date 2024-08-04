package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerGateway;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private SystemTimeProvider systemTimeProvider;
    private TimerGateway timerGateway;
    private RunTaskGateway runTaskGateway;
    private Schedule schedule;


    public AddAutomationService(AutomationRepository automationRepository, SystemTimeProvider systemTimeProvider, TimerGateway timerGateway, RunTaskGateway runTaskGateway, Schedule schedule) {
        this.automationRepository = automationRepository;
        this.systemTimeProvider = systemTimeProvider;
        this.timerGateway = timerGateway;
        this.runTaskGateway = runTaskGateway;
        this.schedule = schedule;
    }

    
    public long add(Automation automation) throws RepositoryException {
        List<Task> tasks = TaskFactory.getInstance(systemTimeProvider.getZoneId()).createTasks(automation);
        if(!tasks.isEmpty()) {
            Set<LocalTime> triggerTimes = new HashSet<>();
            for(Task task: tasks) {
                triggerTimes.add(task.getTriggerTime());
            }

            List<Task> allTasks = this.schedule.getAllTasks();
        
            for (LocalTime triggerTime: triggerTimes) {
                if(!allTasks.isEmpty()) {
                    for (Task t: allTasks) {
                        if(triggerTime.getHour() == t.getTriggerTime().getHour() 
                        && triggerTime.getMinute() == t.getTriggerTime().getMinute() 
                        && triggerTime.getSecond() == t.getTriggerTime().getSecond()) {
                            continue;
                        } else {
                            TimerTask timerTask = new DynamicTimerTask(this.schedule, triggerTime, this.runTaskGateway);
                            this.timerGateway.schedule(timerTask, triggerTime);
                            break;
                        }
                    } 
                } else {
                    TimerTask timerTask = new DynamicTimerTask(this.schedule, triggerTime, this.runTaskGateway);
                    this.timerGateway.schedule(timerTask, triggerTime);
                }
                
                
            }
        }

        return this.automationRepository.add(automation);
    }



}
