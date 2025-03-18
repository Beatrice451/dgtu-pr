package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.beatrice.dgtuProject.model.Task;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.TaskRepository;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, TaskRepository taskRepository, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createTask(String token, Task task) {
        String email = jwtUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);


        return optionalUser.map(user -> {
            task.setUser(user);
            task.
            taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Task successfully created"));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401 Unauthorized", "Invalid user")));
    }
}
