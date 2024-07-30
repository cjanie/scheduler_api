package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;

public class Schedule {

    private TaskRepository taskRepository;

    public Schedule(TaskRepository taskRepository) {
       this.taskRepository = taskRepository;
    }
    
    public List<Task> filterTasksByTriggerTime(LocalTime triggerTime) {
        List<Task> filteredTasks = new ArrayList<>();
        
            for (Task task: this.taskRepository.getTasks()) {
                if (task.getTriggerTime().getHour() == triggerTime.getHour() && 
                    task.getTriggerTime().getMinute() == triggerTime.getMinute() &&
                    task.getTriggerTime().getSecond() == triggerTime.getSecond()
                ) {
                    filteredTasks.add(task);
                }
            }
        
        return filteredTasks;
    }
    
}
