package com.cjanie.scheduler_api.businesslogic.gateways;

import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Task;

public interface TaskRepository {
    List<Task> getTasks();
}
