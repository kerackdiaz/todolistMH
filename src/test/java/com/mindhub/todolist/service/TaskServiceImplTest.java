package com.mindhub.todolist.service;

import com.mindhub.todolist.dtos.TaskRecord;
import com.mindhub.todolist.models.Status;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.User;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks_UserExists() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        var tasks = taskService.getAllTasks("testuser");

        assertNotNull(tasks);
        assertEquals(mockUser.getTasks(), tasks);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testGetAllTasks_UserDoesNotExist() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        var tasks = taskService.getAllTasks("testuser");

        assertNull(tasks);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testNewTask_UserExists() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        TaskRecord taskRecord = new TaskRecord("New Task", "Description", "IN_PROGRESS");
        Map<String, Object> response = taskService.newTask(taskRecord, "testuser");

        assertTrue(response.containsKey("Success"));
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testNewTask_UserDoesNotExist() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        TaskRecord taskRecord = new TaskRecord("New Task", "Description", "IN_PROGRESS");
        Map<String, Object> response = taskService.newTask(taskRecord, "testuser");

        assertTrue(response.containsKey("the email does not exist"));
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_TaskExists() {
        User mockUser = new User();
        Task mockTask = new Task("Old Task", "Old Description", LocalDate.now(), Status.IN_PROGRESS);
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        TaskRecord taskRecord = new TaskRecord("New Task", "New Description", "COMPLETED");
        Map<String, Object> response = taskService.updateTask("testuser", 1L, taskRecord);

        assertTrue(response.containsKey("Success"));
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    void testUpdateTask_TaskDoesNotExist() {
        User mockUser = new User();
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskRecord taskRecord = new TaskRecord("New Task", "New Description", "COMPLETED");
        Map<String, Object> response = taskService.updateTask("testuser", 1L, taskRecord);

        assertTrue(response.containsKey("the task does not exist"));
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    @Test
    void testDeleteTask_TaskExists() {
        User mockUser = new User();
        Task mockTask = new Task("Task to delete", "Description", LocalDate.now(), Status.IN_PROGRESS);
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        Map<String, Object> response = taskService.deleteTask("testuser", 1L);

        assertTrue(response.containsKey("Success"));
        verify(taskRepository, times(1)).delete(mockTask);
    }

    @Test
    void testDeleteTask_TaskDoesNotExist() {
        User mockUser = new User();
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> response = taskService.deleteTask("testuser", 1L);

        assertTrue(response.containsKey("the task does not exist"));
        verify(taskRepository, times(0)).delete(any(Task.class));
    }
}
