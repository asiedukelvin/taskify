package com.taskify.taskify.controller;

import com.taskify.taskify.dto.TaskListWithTasks;
import com.taskify.taskify.model.Task;
import com.taskify.taskify.model.TaskList;
import com.taskify.taskify.repository.TaskRepository;
import com.taskify.taskify.service.TaskListService;
import com.taskify.taskify.service.TaskService;
import com.taskify.taskify.repository.TaskListRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final TaskListService taskListService;
    private final TaskService taskService;
    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public SyncController(TaskListService taskListService, TaskService taskService, TaskListRepository taskListRepository, TaskRepository taskRepository) {
        this.taskListService = taskListService;
        this.taskService = taskService;
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }

    @PutMapping()
    public ResponseEntity<?> syncTaskLists(@RequestBody List<TaskList> taskLists) {
        try {
            for (TaskList list : taskLists) {
                // Save or update TaskList
                taskListService.create(list); // your service already does upsert

                // Nuke old tasks tied to this list
                List<Task> existing = taskService.getByList(list.getId());
                for (Task task : existing) {
                    taskService.delete(task.getId());
                }

                // Save new tasks (if present)
                if (list.getTasks() != null) {
                    for (Task task : list.getTasks()) {
                        task.setTaskListId(list.getId());
                        taskService.create(task);
                    }
                }
            }
            return ResponseEntity.ok("Sync complete");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Something broke and it’s probably your fault.");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskListWithTasks>> syncAll(@PathVariable String userId) {
        List<TaskList> taskLists = taskListRepository.findByUserId(userId);

        List<TaskListWithTasks> result = taskLists.stream()
                .map(taskList -> {
                    List<Task> tasks = taskRepository.findByTaskListId(taskList.getId());
                    return new TaskListWithTasks(taskList, tasks);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
