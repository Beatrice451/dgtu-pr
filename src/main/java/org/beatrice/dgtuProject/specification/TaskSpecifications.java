package org.beatrice.dgtuProject.specification;


import org.beatrice.dgtuProject.model.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    public static Specification<Task> hasStatus(String status) {
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("status"), status)
        );
    }

    public static Specification<Task> hasPriority(String priority) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority)
        );
    }

    public static Specification<Task> hasNameContaining(String name) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%")
        );
    }

    public static Specification<Task>  hasTag(String tag) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.isMember(tag, root.get("tags"))
        );
    }
}
