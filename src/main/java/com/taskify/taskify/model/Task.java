package com.taskify.taskify.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("tasks")
public class Task {

    @Id
    private String id;

    private String title;
    private String notes;
    private Boolean completed;
    private LocalDateTime dueDate;
    private String taskListId;
    private Boolean important;
    private Boolean myDay;

    // Constructors
    public Task() {
        this.completed = false; // Default
    }

    public Task(String title, String notes, LocalDateTime dueDate, String taskListId, Boolean completed, Boolean myDay) {
        this.title = title;
        this.notes = notes;
        this.dueDate = dueDate;
        this.taskListId = taskListId;
        this.completed = completed;
        this.myDay = myDay;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(String taskListId) {
        this.taskListId = taskListId;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {this.important = important;}

    public Boolean getMyDay() { return myDay; }

    public void setMyDay(Boolean myDay) { this.myDay = myDay; }
}
