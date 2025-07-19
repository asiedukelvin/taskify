package com.taskify.taskify.controller;

import com.taskify.taskify.model.Task;
import com.taskify.taskify.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        return ResponseEntity.ok(service.create(task));
    }

    @GetMapping("/{taskListId}")
    public ResponseEntity<List<Task>> getByList(@PathVariable String taskListId) {
        return ResponseEntity.ok(service.getByList(taskListId));
    }

    @PutMapping("/toggle/{id}")
    public ResponseEntity<Task> toggle(@PathVariable String id) {
        return ResponseEntity.ok(service.toggleComplete(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
