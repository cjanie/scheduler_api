package com.cjanie.scheduler_api.adapters;

import java.util.ArrayList;
import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.gateways.TaskRepository;

public class InMemoryTaskRepository implements TaskRepository {

    private List<Task> tasks;


    public InMemoryTaskRepository() {
        this.tasks = new ArrayList<>();
    }


    @Override
    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
}
