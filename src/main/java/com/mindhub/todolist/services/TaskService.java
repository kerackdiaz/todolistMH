package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.TaskRecord;
import com.mindhub.todolist.models.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Task> getAllTasks(String email);

    Map<String, Object> newTask(TaskRecord taskRecord, String email);

    Map<String, Object> updateTask(String email, long taskId, TaskRecord taskRecord);

    Map<String, Object> deleteTask(String email, long taskId);
}
