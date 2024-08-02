package com.cjanie.scheduler_api.businesslogic.gateways;

import java.util.List;

import com.cjanie.scheduler_api.businesslogic.Task;
import com.cjanie.scheduler_api.businesslogic.exceptions.RepositoryException;

public interface TaskRepository {
    List<Task> getTasks();
    List<Long> addTasks(List<Task> tasks) throws RepositoryException;
}
