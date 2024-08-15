package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Status;
import com.mindhub.todolist.models.Task;

import java.time.LocalDate;

public class TaskDTO {
    private final long id;
    private final String Title;
    private final String Description;
    private final LocalDate Date;
    private final Status status;


    public TaskDTO(Task task) {
        this.id = task.getId();
        this.Title = task.getTitle();
        this.Description = task.getDescription();
        this.Date = task.getDate();
        this.status = task.getStatus();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public LocalDate getDate() {
        return Date;
    }

    public Status getStatus() {
        return status;
    }


}
