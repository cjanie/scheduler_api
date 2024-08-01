package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;
import com.cjanie.scheduler_api.businesslogic.utils.LocalTimeUtil;

public class Schedule {

    private TaskRepository taskRepository;

    public Schedule(TaskRepository taskRepository) {
       this.taskRepository = taskRepository;
    }
    
    public List<Task> filterTasksByTriggerTime(LocalTime triggerTime) {
        List<Task> filteredTasks = new ArrayList<>();
        if(!this.taskRepository.getTasks().isEmpty()) {
            for (Task task: this.taskRepository.getTasks()) {
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

    public LocalTime getNextTriggerTime(LocalTime lastTriggerTime) {
        List<Task> tasks = this.taskRepository.getTasks();
        if(!tasks.isEmpty()) {
            List<LocalTime> triggerTimes = new ArrayList<>();

            for (Task task: tasks) {
                triggerTimes.add(task.getTriggerTime());
            }
            return LocalTimeUtil.getNextTime(lastTriggerTime, triggerTimes);
        }
        return null;
        
    }
    
}
