package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.TaskRecord;
import com.mindhub.todolist.models.Status;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.User;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Task> getAllTasks(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user.getTasks();
    }

    @Override
    public Map<String, Object> newTask(TaskRecord taskRecord) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByEmail(taskRecord.email());
        if (user == null) {
            response.put("the email does not exist", ResponseEntity.badRequest());
            return response;
        }
        try {
            Task task = new Task(taskRecord.Title(), taskRecord.Description(), LocalDate.now(), Status.IN_PROGRESS);
            task.setUser(user);
            taskRepository.save(task);
            response.put("Success", ResponseEntity.status(201).body("Task created successfully"));
        } catch (Exception e) {
            response.put("unexpected error", ResponseEntity.internalServerError());
            return response;
        }
        return response;
    }


    @Override
    public Map<String, Object> updateTask( long taskId, TaskRecord taskRecord) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByEmail(taskRecord.email());
        if (user == null) {
            response.put("the email does not exist", ResponseEntity.badRequest());
            return response;
        }
        try {
            Task task = taskRepository.findById(taskId).orElse(null);
            if (task == null) {
                response.put("the task does not exist", ResponseEntity.badRequest());
                return response;
            }
            if(!task.getTitle().equals(taskRecord.Title())){
                task.setTitle(taskRecord.Title());
            }
            if (!task.getDescription().equals(taskRecord.Description())){
                task.setDescription(taskRecord.Description());
            }
            if(!task.getStatus().equals(Status.valueOf(taskRecord.status()))){
                task.setStatus(Status.valueOf(taskRecord.status()));
            }
            taskRepository.save(task);
            response.put("Success", ResponseEntity.status(200).body("Task updated successfully"));
        } catch (Exception e) {
            response.put("unexpected error", ResponseEntity.internalServerError());
            return response;
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteTask(long taskId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Task task = taskRepository.findById(taskId).orElse(null);
            if (task == null) {
                response.put("the task does not exist", ResponseEntity.badRequest());
                return response;
            }
            taskRepository.delete(task);
            response.put("Success", ResponseEntity.status(200).body("Task deleted successfully"));
        } catch (Exception e) {
            response.put("unexpected error", ResponseEntity.internalServerError());
            return response;
        }
        return response;
    }
}