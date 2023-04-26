package com.george.todoapp.model;

import java.time.LocalDate;

public class Todo {
    private Long id;
    private String subject;
    private String username;
    private String description;
    private LocalDate todoDate;
    private boolean status;

    protected Todo() {

    }

    public Todo(long id, String subject, String username, String description, LocalDate todoDate, boolean isDone) {
        super();
        this.id = id;
        this.subject = subject;
        this.username = username;
        this.description = description;
        this.todoDate = todoDate;
        this.status = isDone;
    }

    public Todo(String subject, String username, String description, LocalDate todoDate, boolean isDone) {
        super();
        this.subject = subject;
        this.username = username;
        this.description = description;
        this.todoDate = todoDate;
        this.status = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Todo other = (Todo) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
