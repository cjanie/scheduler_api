package com.cjanie.scheduler_api.businesslogic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<Task> tasks;


    public Schedule() {
       this.tasks = new ArrayList<>();
    }


    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> filterTasksByTriggerTime(LocalTime triggerTime) {
        List<Task> filteredTasks = new ArrayList<>();
        
            for (Task task: this.tasks) {
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
