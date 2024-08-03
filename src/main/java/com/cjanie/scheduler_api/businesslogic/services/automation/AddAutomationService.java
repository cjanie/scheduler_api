package com.cjanie.scheduler_api.businesslogic.services.automation;

import com.cjanie.scheduler_api.businesslogic.services.GetTickTime;
import com.cjanie.scheduler_api.businesslogic.services.TickServiceState;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.cjanie.scheduler_api.businesslogic.Automation;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;
import com.cjanie.scheduler_api.businesslogic.gateways.AutomationRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.RunTaskGateway;
import com.cjanie.scheduler_api.businesslogic.gateways.SystemTimeProvider;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;

public class AddAutomationService {

    private AutomationRepository automationRepository;
    private TaskRepository taskRepository;
    private SystemTimeProvider systemTimeProvider;
    private TaskFactory taskFactory;
    private GetTickTime tickServiceState;
    private Map<Timer, TimerTask> timerTaskMapping;
    private RunTaskGateway runTaskGateway;


    public AddAutomationService(
        AutomationRepository automationRepository, 
        TaskRepository taskRepository, 
        SystemTimeProvider systemTimeProvider,
        GetTickTime tickServiceState,
        RunTaskGateway runTaskGateway
        ) {
        this.automationRepository = automationRepository;
        this.taskRepository = taskRepository;
        this.systemTimeProvider = systemTimeProvider;
        this.taskFactory = TaskFactory.getInstance(this.systemTimeProvider.getZoneId());
        this.tickServiceState = tickServiceState;
        this.timerTaskMapping = new HashMap<>();
        this.runTaskGateway = runTaskGateway;
    }

    public long add(Automation automation) throws RepositoryException {
        List<Task> tasks = this.taskFactory.createTasks(automation);
        this.taskRepository.addTasks(tasks);

        LocalTime tickTime = this.tickServiceState.getTickTime();


        if(tickTime != null) {
            LocalDateTime tickDateTime = LocalTimeUtil.timeToDateTime(tickTime, this.systemTimeProvider);

            for (Task task: tasks) {

                LocalTime triggerTime = task.getTriggerTime();
                LocalDateTime triggerDateTime = LocalTimeUtil.timeToDateTime(triggerTime, this.systemTimeProvider);

                LocalDateTime now = this.systemTimeProvider.nowDateTime();
            
                if(triggerDateTime.isAfter(now) && 
                    triggerDateTime.isBefore(tickDateTime)) {

                    Date triggerDate = Date.from(triggerDateTime.atZone(this.systemTimeProvider.getZoneId()).toInstant());

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                task.run(runTaskGateway);
                            } catch (GatewayException e) {
                                e.printStackTrace();
                            }
                            
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(timerTask, triggerDate);
                    timerTaskMapping.put(timer, timerTask);

                }
            }
        }

        return this.automationRepository.add(automation);
    }

}
