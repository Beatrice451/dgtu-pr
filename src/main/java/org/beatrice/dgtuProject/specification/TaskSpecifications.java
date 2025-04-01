package org.beatrice.dgtuProject.specification;


import jakarta.persistence.criteria.Join;
import org.beatrice.dgtuProject.model.Tag;
import org.beatrice.dgtuProject.model.Task;
import org.beatrice.dgtuProject.model.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            try {
                TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
                return criteriaBuilder.equal(root.get("status"), taskStatus);
            } catch (IllegalArgumentException e) {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Task> hasPriority(String priority) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, Tag> tagJoin = root.join("tags");

            // Сопоставляем строковые приоритеты с ID тегов
            Integer priorityTagId = switch (priority.toLowerCase()) {
                case "low" -> 11;
                case "medium" -> 10;
                case "high" -> 9;
                default -> null;
            };

            if (priorityTagId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(tagJoin.get("id"), priorityTagId);
        };
    }

    public static Specification<Task> hasNameContaining(String name) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%")
        );
    }

    public static Specification<Task>  hasTag(String tag) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, Tag> tagJoin = root.join("tags");
            return criteriaBuilder.equal(tagJoin.get("name"), tag);
        };
    }
}
