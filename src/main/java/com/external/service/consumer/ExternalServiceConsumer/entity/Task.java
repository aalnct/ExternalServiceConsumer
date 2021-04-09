package com.external.service.consumer.ExternalServiceConsumer.entity;

import javax.persistence.*;

@Entity
@Table(name = "todoitems")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todoID;

    @Column(name = "userid")
    private int userId;

    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed")
    private boolean completed;

    public int getTodoID() {
        return todoID;
    }

    public void setTodoID(int todoID) {
        this.todoID = todoID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
