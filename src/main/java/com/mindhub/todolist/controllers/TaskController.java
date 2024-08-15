package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.TaskRecord;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Get all tasks", description = "Get all tasks from your account")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks(@RequestBody String userName) {
        return ResponseEntity.ok(taskService.getAllTasks(userName).stream().map(TaskDTO::new).collect(Collectors.toList()));
    }

    @Operation(summary = "Create a new task", description = "Create a new task")
    @PostMapping("/new")
    public ResponseEntity<?> newTask(@RequestBody TaskRecord taskRecord) {
        return ResponseEntity.ok(taskService.newTask(taskRecord));
    }

    @Operation(summary = "Update task", description = "Update any field of a task by its id")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable long id, @RequestBody TaskRecord taskRecord) {
        return ResponseEntity.ok(taskService.updateTask( id, taskRecord));
    }


    @Operation(summary = "Delete task", description = "Delete a task by its id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}