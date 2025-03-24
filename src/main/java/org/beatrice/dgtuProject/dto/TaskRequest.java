package org.beatrice.dgtuProject.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class TaskRequest {
    private String name;
    private String description;
    private LocalDateTime deadline;
    private String status;
    private Set<Long> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Long> getTags() {
        return tags;
    }

    public void setTags(Set<Long> tags) {
        this.tags = tags;
    }
}
