package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.TaskRequest;
import org.beatrice.dgtuProject.dto.TaskResponse;
import org.beatrice.dgtuProject.exception.DeadlinePassedException;
import org.beatrice.dgtuProject.exception.InvalidTaskStatusException;
import org.beatrice.dgtuProject.exception.UserNotFoundException;
import org.beatrice.dgtuProject.model.Tag;
import org.beatrice.dgtuProject.model.Task;
import org.beatrice.dgtuProject.model.TaskStatus;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.TagRepository;
import org.beatrice.dgtuProject.repository.TaskRepository;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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



    public void createTask(String header, TaskRequest request) {
        String token = jwtUtil.getTokenFromHeader(header);

        User user = userRepository.findByEmail(jwtUtil.getEmailFromToken(token))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.tags()));
        TaskStatus status;
        try {
            status = TaskStatus.valueOf(request.status().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidTaskStatusException("Invalid task status: " + request.status());
        }


        LocalDateTime deadline = request.deadline();
        if (deadline.isBefore(LocalDateTime.now())) {
            throw new DeadlinePassedException("Deadline cannot be in the past");
        }


        Task task = new Task();
        task.setTags(tags);
        task.setUser(user);
        task.setStatus(status);
        task.setDeadline(request.deadline());
        task.setDescription(request.description());
        task.setName(request.name());

        taskRepository.save(task);
    }

    public List<TaskResponse> GetTasks(String header) {
        String token = jwtUtil.getTokenFromHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        List<Task> tasks = taskRepository.findAllByUserEmail(email);
        return tasks.stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
