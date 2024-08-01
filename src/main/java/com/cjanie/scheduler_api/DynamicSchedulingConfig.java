package com.cjanie.scheduler_api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.cjanie.scheduler_api.adapters.InMemoryTaskRepository;
import com.cjanie.scheduler_api.adapters.RealTimeProvider;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.TimeProvider;
import com.cjanie.scheduler_api.businesslogic.utils.LocalDateTimeUtil;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

// https://www.baeldung.com/spring-scheduled-tasks

@Configuration
@EnableScheduling
@ComponentScan("com.cjanie.scheduler_api")
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    private static String TAG = DynamicSchedulingConfig.class.getName();

    private TickService tickService;

    // Dependencies injection
    
    public TickService tickService() {
        return new TickService(taskRepository(), timeProvider());
    }

    
    public TaskRepository taskRepository() {
        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();
        
        Task task1 = new TaskPowerOn(LocalTime.of(1, 17, 0));
        Task task2 = new TaskPowerOff(LocalTime.of(1, 18, 0));
        Task task3 = new TaskPowerOff(LocalTime.of(1, 21, 0));
        taskRepository.setTasks(List.of(task1, task2, task3));
        
        
        return taskRepository;
    }

    public TimeProvider timeProvider() {
        return new RealTimeProvider();
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    // Constructor
    public DynamicSchedulingConfig() {
        this.tickService = tickService();
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());


        // add a Runnable task and a Trigger implementation to recalculate the nextExecutionTime after the end of each execution

        taskRegistrar.addTriggerTask(
            () -> {
                tickService.tick();
            },
            (triggerContext) -> {

                LocalTime nextTickTime = this.tickService.getNextTickTime();
                Instant nextExcecution;
                if(nextTickTime != null) {
                    nextExcecution = LocalTimeUtil.convertLocalTimeToInstant(nextTickTime);
                    System.out.println("LOG " + TAG + " : next Excecution time = " + nextTickTime);
                } else {
                    nextExcecution = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().plusMillis(this.tickService.getDefaultDelayMillis());
                    System.out.println("LOG " + TAG + " : next Excecution delay = " + this.tickService.getDefaultDelayMillis());
                }
                
                return nextExcecution;
            }
        );    
    }
    
}
