package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.services.automation.TaskFactory;

public class Schedule {

    private AutomationRepository automationRepository;

    private SystemTimeProvider timeProvider;

    private TaskFactory taskFactory;

    public Schedule(AutomationRepository automationRepository, SystemTimeProvider timeProvider) {
       this.automationRepository = automationRepository;
       this.timeProvider = timeProvider;
       this.taskFactory = TaskFactory.getInstance(this.timeProvider.getZoneId());
    }

    public List<Task> getAllTasks() throws RepositoryException {
        List<Automation> automations = this.automationRepository.get();
        return this.taskFactory.createTasks(automations);
    }
    
    public List<Task> filterTasksByTriggerTime(LocalTime triggerTime) throws RepositoryException {
    
        List<Task> allTasks = getAllTasks();
        
        List<Task> filteredTasks = new ArrayList<>();
        if(!allTasks.isEmpty()) {
            for (Task task: allTasks) {
                if (task.getTriggerTime().getHour() == triggerTime.getHour() && 
                    task.getTriggerTime().getMinute() == triggerTime.getMinute() &&
                    task.getTriggerTime().getSecond() == triggerTime.getSecond()
                ) {
                    filteredTasks.add(task);
                }
            }
        }
        return filteredTasks;
    }
    
}
