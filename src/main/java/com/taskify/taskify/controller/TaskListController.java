package com.taskify.taskify.controller;

import com.taskify.taskify.model.TaskList;
import com.taskify.taskify.service.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasklists")
public class TaskListController {
    private final TaskListService service;

    public TaskListController(TaskListService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskList> create(@RequestBody TaskList list) {
        return ResponseEntity.ok(service.create(list));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskList>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
