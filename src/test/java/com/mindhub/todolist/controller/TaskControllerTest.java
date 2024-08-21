package com.mindhub.todolist.controller;

import com.mindhub.todolist.controllers.TaskController;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.TaskRecord;
import com.mindhub.todolist.models.Status;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("user@example.com");
    }

    @Test
    void getAllTasks() {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1", LocalDate.now(), Status.PENDING);
        Task task2 = new Task("Task 2", "Description 2", LocalDate.now(), Status.COMPLETED);
        List<Task> tasks = Arrays.asList(task1, task2);
        List<TaskDTO> taskDTOs = tasks.stream().map(TaskDTO::new).collect(Collectors.toList());

        when(taskService.getAllTasks(anyString())).thenReturn(tasks);

        // Act
        ResponseEntity<?> response = taskController.getAllTasks();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(taskDTOs, response.getBody());
        verify(taskService).getAllTasks("user@example.com");
    }

    @Test
    void newTask() {
        // Arrange
        TaskRecord taskRecord = new TaskRecord("Test Task", "Description", "IN_PROGRESS");
        Task newTask = new Task("Test Task", "Description", LocalDate.now(), Status.PENDING);
        TaskDTO taskDTO = new TaskDTO(newTask);
        when(taskService.newTask(any(TaskRecord.class), anyString())).thenReturn((Map<String, Object>) newTask);

        // Act
        ResponseEntity<?> response = taskController.newTask(taskRecord);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(taskDTO.getTitle(), ((TaskDTO)response.getBody()).getTitle());
        assertEquals(taskDTO.getDescription(), ((TaskDTO)response.getBody()).getDescription());
        verify(taskService).newTask(taskRecord, "user@example.com");
    }

    @Test
    void updateTask() {
        // Arrange
        long taskId = 1L;
        TaskRecord taskRecord = new TaskRecord("Updated Task", "Updated Description", "COMPLETED");
        Task updatedTask = new Task("Updated Task", "Updated Description", LocalDate.now(), Status.COMPLETED);
        updatedTask.setId(taskId);
        TaskDTO updatedTaskDTO = new TaskDTO(updatedTask);
        when(taskService.updateTask(anyString(), anyLong(), any(TaskRecord.class))).thenReturn((Map<String, Object>) updatedTask);

        // Act
        ResponseEntity<?> response = taskController.updateTask(taskId, taskRecord);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedTaskDTO.getTitle(), ((TaskDTO)response.getBody()).getTitle());
        assertEquals(updatedTaskDTO.getDescription(), ((TaskDTO)response.getBody()).getDescription());
        verify(taskService).updateTask("user@example.com", taskId, taskRecord);
    }




}