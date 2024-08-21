package com.mindhub.todolist.models;



import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Title;
    @Column(length = 500)
    private String Description;
    private LocalDate Date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {
    }

    public Task(String title, String description, LocalDate date, Status status) {
        this.Title = title;
        this.Description = description;
        this.Date = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(long taskId) {
    }
}
