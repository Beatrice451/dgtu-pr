package org.beatrice.dgtuProject.controller;


import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.TaskRequest;
import org.beatrice.dgtuProject.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {


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
    public ResponseEntity<?> getTasks(@RequestHeader("Authorization") String header) {
        List<?> tasks = taskService.getTasks(header);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
