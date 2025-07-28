package com.taskify.taskify.dto;

import com.taskify.taskify.model.Task;
import com.taskify.taskify.model.TaskList;

import java.util.List;

public class TaskListWithTasks {
    private String id;
    private String title;
    private String userId;
    private List<Task> tasks;

    public TaskListWithTasks(TaskList taskList, List<Task> tasks) {
        this.id = taskList.getId();
        this.title = taskList.getTitle();
        this.userId = taskList.getUserId();
        this.tasks = tasks;
    }

    // Getters and setters
}