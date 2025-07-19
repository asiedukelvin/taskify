package com.taskify.taskify.repository;

import com.taskify.taskify.model.TaskList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskListRepository extends MongoRepository<TaskList, String> {
    List<TaskList> findByUserId(String userId);
}