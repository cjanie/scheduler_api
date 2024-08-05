package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Schedule;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private SystemTimeProvider systemTimeProvider;
    private TimerGateway timerGateway;
    private RunTaskGateway runTaskGateway;
    private Schedule schedule;
    private TimerTaskRepository timerTaskRepository;



    public AddAutomationService(AutomationRepository automationRepository, SystemTimeProvider systemTimeProvider, TimerGateway timerGateway, RunTaskGateway runTaskGateway, Schedule schedule, TimerTaskRepository timerTaskRepository) {
        this.automationRepository = automationRepository;
        this.systemTimeProvider = systemTimeProvider;
        this.timerGateway = timerGateway;
        this.runTaskGateway = runTaskGateway;
        this.schedule = schedule;
        this.timerTaskRepository = timerTaskRepository;
    }
    
    
    public long add(Automation automation) throws RepositoryException {
        this.sheduleTimer(automation);
        

        return this.automationRepository.add(automation);
    }

    private Set<LocalTime> getTriggerTimes(List<Task> tasks) {
        Set<LocalTime> triggerTimes = new HashSet<>();
        if(!tasks.isEmpty()) {
            for(Task task: tasks) {
                triggerTimes.add(task.getTriggerTime());
            }
        }
        return triggerTimes;
    }

    private void sheduleTimer(Automation automation) throws RepositoryException {
        List<Task> tasks = TaskFactory.getInstance(systemTimeProvider.getZoneId()).createTasks(automation);
        Set<LocalTime> triggerTimes = this.getTriggerTimes(tasks);
        if(!triggerTimes.isEmpty()) {

            List<Task> allTasks = this.schedule.getAllTasks();
        
            for (LocalTime triggerTime: triggerTimes) {
                if(!allTasks.isEmpty()) {
                    for (Task t: allTasks) {
                        if(LocalTimeUtil.isTheSameTime(triggerTime, t.getTriggerTime())) {
                            continue;
                        } else {
                            this.handleNewTimerTask(triggerTime);
                            break;
                        }
                    } 
                } else {
                    this.handleNewTimerTask(triggerTime);
                }
            }
        }
    }

    private long handleNewTimerTask(LocalTime triggerTime) {
        TimerTask timerTask = new DynamicTimerTask(this.schedule, triggerTime, this.runTaskGateway);
        this.timerGateway.schedule(timerTask, triggerTime);
        return this.timerTaskRepository.add(triggerTime, timerTask);
    }

}
