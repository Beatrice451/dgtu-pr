package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.StatusRequest;
import org.beatrice.dgtuProject.dto.TaskRequest;
import org.beatrice.dgtuProject.dto.TaskResponse;
import org.beatrice.dgtuProject.exception.*;
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
import java.util.Objects;
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

    public List<TaskResponse> getTasks(String header) {
        String token = jwtUtil.getTokenFromHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        List<Task> tasks = taskRepository.findAllByUserEmail(email);
        return tasks.stream().map(TaskResponse::fromEntity).collect(Collectors.toList());
    }

    public void deleteTask(Long id, String header) {
        String token = jwtUtil.getTokenFromHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with such id not found"));
        if (!Objects.equals(user.getId(), task.getUser().getId())) {
            throw new NoAccessEexception("No access to this task:" + task.getId());
        }
        taskRepository.deleteById(task.getId());
    }

    public TaskResponse patchTask(Long id, StatusRequest status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        try {
            task.setStatus(TaskStatus.valueOf(status.status().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidTaskStatusException("Invalid task status: " + status.status());
        }

        taskRepository.save(task);
        return new TaskResponse(
                task.getName(),
                task.getUser().getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getCreatedAt(),
                task.getStatus(),
                task.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toSet())
        );
    }
}
