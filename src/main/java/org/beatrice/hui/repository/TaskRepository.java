package org.beatrice.hui.repository;

import org.beatrice.hui.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
