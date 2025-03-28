package org.beatrice.dgtuProject.dto;

import org.beatrice.dgtuProject.model.Tag;
import org.beatrice.dgtuProject.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record TaskRequest(
        String name,
        String description,
        LocalDateTime deadline,
        String status,
        Set<Long> tags
) {
    public static TaskRequest fromEntity(Task task) {

        return new TaskRequest(
                task.getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getStatus().toString(),
                task.getTags().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toSet())
        );
    }
}
