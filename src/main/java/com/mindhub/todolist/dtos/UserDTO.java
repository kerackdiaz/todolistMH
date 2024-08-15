package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String username, email;
    private final List<TaskDTO> tasks;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.id = user.getId();
        this.tasks = TaskDTO(user.getTasks());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    private List<TaskDTO> TaskDTO(List<Task> tasks) {
        return tasks.stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }
}


