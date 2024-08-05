package com.cjanie.scheduler_api.businesslogic.services.automation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private SystemTimeProvider systemTimeProvider;
    private RunTaskGateway runTaskGateway;
    private TimerTaskRepository timerTaskRepository;



    public AddAutomationService(
        AutomationRepository automationRepository, 
        SystemTimeProvider systemTimeProvider, 
        RunTaskGateway runTaskGateway, 
        TimerTaskRepository timerTaskRepository) {
        this.automationRepository = automationRepository;
        this.systemTimeProvider = systemTimeProvider;
        this.runTaskGateway = runTaskGateway;
        this.timerTaskRepository = timerTaskRepository;
    }
    
    
    public long add(Automation automation) throws RepositoryException {
        long automationId = this.automationRepository.add(automation);
        automation.setId(automationId);
        this.sheduleTimer(automation);
        
        return automationId;
    }

    private void sheduleTimer(Automation automation) throws RepositoryException {
        List<Task> tasks = TaskFactory.getInstance(systemTimeProvider.getZoneId()).createTasks(automation);
        Set<LocalTime> triggerTimes = this.getTriggerTimes(tasks);
        for(LocalTime triggerTime: triggerTimes) {
            List<Task> tasksForTime = this.filterTasksByTriggerTime(tasks, triggerTime);
            IdentifiedTimerTask timerTask = new IdentifiedTimerTask(automation.getId(), tasksForTime, runTaskGateway);
            Timer timer = new Timer();
            timer.schedule(timerTask, LocalTimeUtil.timeToDate(triggerTime, systemTimeProvider));
            this.timerTaskRepository.add(timerTask, timer);
        }
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

    private List<Task> filterTasksByTriggerTime(List<Task> tasks, LocalTime triggerTime) {
        List<Task> tasksForTime = new ArrayList<>();
        for (Task task: tasks) {
            if(LocalTimeUtil.isTheSameTime(triggerTime, task.getTriggerTime())) {
                tasksForTime.add(task);
            }
        }
        return tasksForTime;
    } 

}
