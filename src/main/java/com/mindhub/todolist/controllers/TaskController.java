package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.TaskRecord;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Get all tasks", description = "Get all tasks from your account")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(taskService.getAllTasks(userMail).stream().map(TaskDTO::new).collect(Collectors.toList()));
    }

    @Operation(summary = "Create a new task", description = "Create a new task")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/new")
    public ResponseEntity<?> newTask(@RequestBody TaskRecord taskRecord) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(taskService.newTask(taskRecord, user));
    }


    @Operation(summary = "Update task", description = "Update any field of a task by its id")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable long id, @RequestBody TaskRecord taskRecord) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(taskService.updateTask(userMail, id, taskRecord));
    }


    @Operation(summary = "Delete task", description = "Delete a task by its id")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(taskService.deleteTask(userMail, id));
    }
}