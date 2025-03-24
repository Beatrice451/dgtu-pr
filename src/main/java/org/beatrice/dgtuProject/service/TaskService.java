package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.TaskRequest;
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




    /**
     * Creates a new task (Task) for the user identified by the provided token.
     *
     * @param token   JWT token used to identify the user.
     * @param request DTO request containing task details.
     * @return The saved {@link Task} object.
     * @throws UserNotFoundException    if the user is not found by email.
     * @throws IllegalArgumentException if an invalid task status is provided.
     */

    public Task createTask(String token, TaskRequest request) {
        User user = userRepository.findByEmail(jwtUtil.getEmailFromToken(token))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTags()));
        TaskStatus status;
        try {
            status = TaskStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidTaskStatusException("Invalid task status: " + request.getStatus());
        }


        LocalDateTime deadline = request.getDeadline();
        if (deadline.isBefore(LocalDateTime.now())) {
            throw new DeadlinePassedException("Deadline cannot be in the past");
        }


        Task task = new Task();
        task.setTags(tags);
        task.setUser(user);
        task.setStatus(status);
        task.setDeadline(request.getDeadline());
        task.setDescription(request.getDescription());
        task.setName(request.getName());

        return taskRepository.save(task);
    }
}
