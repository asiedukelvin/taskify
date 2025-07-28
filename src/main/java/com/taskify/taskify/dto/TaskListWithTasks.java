package com.taskify.taskify.dto;

import com.taskify.taskify.model.Task;
import com.taskify.taskify.model.TaskList;

import java.util.List;

public class TaskListWithTasks {
    private TaskList taskList;
    private List<Task> tasks;

    public TaskListWithTasks() {}

    public TaskListWithTasks(TaskList taskList, List<Task> tasks) {
        this.taskList = taskList;
        this.tasks = tasks;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
