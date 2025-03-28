package org.beatrice.dgtuProject.dto;

import org.beatrice.dgtuProject.model.Tag;
import org.beatrice.dgtuProject.model.Task;
import org.beatrice.dgtuProject.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record TaskResponse(
        String name,
        String author,
        String description,
        LocalDateTime deadline,
        LocalDateTime createdAt,
        TaskStatus status,
        Set<String> tags
) {
    public static TaskResponse fromEntity(Task task) {
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

