package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.beatrice.dgtuProject.model.Tag;
import org.beatrice.dgtuProject.model.Task;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.TagRepository;
import org.beatrice.dgtuProject.repository.TaskRepository;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {

    private final JwtUtil jwtUtil;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public TaskService(JwtUtil jwtUtil, TaskRepository taskRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.jwtUtil = jwtUtil;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public ResponseEntity<?> createTask(String token, Task task, Set<Long> tagIds) {
        String email = jwtUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        return optionalUser.map(user -> {
            task.setUser(user);

            Set<Tag> existingTags = new HashSet<>(tagRepository.findAllById(tagIds));
            task.setTags(existingTags);

            // ОТЛАДОЧНЫЙ ВЫВОД
            System.out.println("Количество тегов: " + existingTags.size());
            existingTags.forEach(tag -> System.out.println("Тег ID: " + tag.getId()));


            taskRepository.save(task);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Task created successfully"));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401 Unauthorized", "Invalid user")));
    }
}
