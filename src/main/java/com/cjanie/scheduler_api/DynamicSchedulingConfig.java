package com.cjanie.scheduler_api;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

// https://www.baeldung.com/spring-scheduled-tasks

@Configuration
@EnableScheduling
@ComponentScan("com.cjanie.scheduler_api")
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    @Autowired
    private TickService tickService;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());

        // add a Runnable task and a Trigger implementation to recalculate the nextExecutionTime after the end of each execution
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
