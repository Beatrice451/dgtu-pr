package org.beatrice.dgtuProject.controller;


import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.StatusRequest;
import org.beatrice.dgtuProject.dto.TaskRequest;
import org.beatrice.dgtuProject.dto.TaskResponse;
import org.beatrice.dgtuProject.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestHeader("Authorization") String header, @RequestBody TaskRequest request) {
        taskService.createTask(header, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Task created successfully"));
    }

    @GetMapping
    public ResponseEntity<?> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String name
    ) {
        logger.debug("Getting request: /tasks [GET]. Params: status = {}, priority = {}, tag = {}, name = {}",
                status, priority, tag, name);
        List<?> tasks = taskService.getTasks(status, priority, tag, name);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String header) {
        taskService.deleteTask(id, header);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestBody StatusRequest status) {
        TaskResponse updatedTask = taskService.patchTask(id, status);
        return ResponseEntity.ok(updatedTask);
    }
}
