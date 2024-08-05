package com.cjanie.scheduler_api.businesslogic.services.timertask;

import java.util.List;
import java.util.Timer;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.IdentifiedTimerTask;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.factories.TaskFactory;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TimerTaskRepository;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

public class ScheduleTimerTaskService {

    private SystemTimeProvider systemTimeProvider;
    private RunTaskGateway runTaskGateway;
    private TimerTaskRepository timerTaskRepository;


    public ScheduleTimerTaskService(
        SystemTimeProvider systemTimeProvider, 
        RunTaskGateway runTaskGateway, 
        TimerTaskRepository timerTaskRepository
        ) {
        this.systemTimeProvider = systemTimeProvider;
        this.runTaskGateway = runTaskGateway;
        this.timerTaskRepository = timerTaskRepository;
    }


    public void sheduleTimer(Automation automation) throws RepositoryException {
        List<Task> tasks = TaskFactory.getInstance(systemTimeProvider.getZoneId()).createTasks(automation);
        
        for(Task task: tasks) {
            IdentifiedTimerTask timerTask = new IdentifiedTimerTask(
                automation.getId(), 
                task, 
                this.runTaskGateway
                );
            Timer timer = new Timer();
            timer.schedule(timerTask, LocalTimeUtil.timeToDate(task.getTriggerTime(), this.systemTimeProvider));
            this.timerTaskRepository.add(timerTask, timer);
        }
    }
    
}
