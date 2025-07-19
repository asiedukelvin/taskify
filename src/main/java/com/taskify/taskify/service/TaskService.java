package com.taskify.taskify.service;

import com.taskify.taskify.model.Task;
import com.taskify.taskify.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public Task create(Task task) {
        return repo.save(task);
    }

    public List<Task> getByList(String taskListId) {
        return repo.findByTaskListId(taskListId);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public Task toggleComplete(String id) {
        Task task = repo.findById(id).orElseThrow();
        task.setCompleted(!task.isCompleted());
        return repo.save(task);
    }
}
