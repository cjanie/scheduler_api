package com.cjanie.scheduler_api.adapters.primary;

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

import com.cjanie.scheduler_api.DI;
import com.cjanie.scheduler_api.adapters.secondary.InMemoryTaskRepository;
import com.cjanie.scheduler_api.adapters.secondary.RealTimeProvider;
import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOff;
import com.cjanie.scheduler_api.businesslogic.TaskPowerOn;
import com.cjanie.scheduler_api.businesslogic.exceptions.GatewayException;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.gateways.GenericTimeProvider;
import com.cjanie.scheduler_api.businesslogic.services.TickService;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

// https://www.baeldung.com/spring-scheduled-tasks

@Configuration
@EnableScheduling
@ComponentScan("com.cjanie.scheduler_api")
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    private static String TAG = DynamicSchedulingConfig.class.getName();

    private TickService tickService;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    // Constructor
    public DynamicSchedulingConfig() {
        this.tickService = DI.getInstance().tickService();
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());


        // add a Runnable task and a Trigger implementation to recalculate the nextExecutionTime after the end of each execution

        taskRegistrar.addTriggerTask(
            () -> {
                try {
                    tickService.tick();
                } catch (GatewayException e) {
                    e.printStackTrace();
                }
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
