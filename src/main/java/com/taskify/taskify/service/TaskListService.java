package com.taskify.taskify.service;

import com.taskify.taskify.model.TaskList;
import com.taskify.taskify.repository.TaskListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListService {
    private final TaskListRepository repo;

    public TaskListService(TaskListRepository repo) {
        this.repo = repo;
    }

    public TaskList create(TaskList list) {
        return repo.save(list);
    }

    public List<TaskList> getByUser(String userId) {
        return repo.findByUserId(userId);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
