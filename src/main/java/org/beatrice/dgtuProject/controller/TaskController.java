package org.beatrice.dgtuProject.controller;


import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.TaskRequest;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.beatrice.dgtuProject.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {


    private final TaskService taskService;
    private final JwtUtil jwtUtil;

    public TaskController(TaskService taskService, JwtUtil jwtUtil) {
        this.taskService = taskService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestHeader("Authorization") String header, @RequestBody TaskRequest request) {
        String token = jwtUtil.getTokenFromHeader(header);
        taskService.createTask(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Task created successfully"));
    }

}
