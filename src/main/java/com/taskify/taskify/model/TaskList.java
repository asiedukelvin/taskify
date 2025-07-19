package com.taskify.taskify.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tasklists")
public class TaskList {

    @Id
    private String id;

    private String title;
    private String userId;

    // Constructors
    public TaskList() {}

    public TaskList(String name, String userId) {
        this.title = name;
        this.userId = userId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
