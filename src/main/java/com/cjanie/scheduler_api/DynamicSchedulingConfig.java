package com.cjanie.scheduler_api;

import java.time.Instant;
import java.time.LocalTime;
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
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;

// https://www.baeldung.com/spring-scheduled-tasks

@Configuration
@EnableScheduling
@ComponentScan("com.cjanie.scheduler_api")
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    @Bean
    public TickService tickService() {
        return new TickService(taskRepository());
    }

    @Bean 
    public TaskRepository taskRepository() {
        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();
        Task task1 = new TaskPowerOn(LocalTime.of(20, 0, 0));
        Task task2 = new TaskPowerOff(LocalTime.of(8, 0, 0));
        taskRepository.setTasks(List.of(task1, task2));
        return taskRepository;
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());


        // add a Runnable task and a Trigger implementation to recalculate the nextExecutionTime after the end of each execution
        TickService tickService = tickService();

        taskRegistrar.addTriggerTask(
            () -> tickService.tick(),
            (triggerContext) -> {
                Instant nextExecution = null;
                if(triggerContext != null) {
                    Instant lastCompletion = triggerContext.lastCompletion();
                    if(lastCompletion != null) {
                        nextExecution = lastCompletion.plusMillis(tickService.getDelay());
                    } else {
                        nextExecution = new Date().toInstant().plusMillis(tickService.getDelay());
                    }    
                } else {
                    nextExecution = new Date().toInstant().plusMillis(tickService.getDelay());
                }
                return nextExecution;
            }
        );    
    }
    
}
