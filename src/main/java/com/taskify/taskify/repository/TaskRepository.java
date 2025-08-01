package com.taskify.taskify.repository;

import com.taskify.taskify.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByTaskListId(String taskListId);
}

