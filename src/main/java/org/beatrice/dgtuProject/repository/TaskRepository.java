package org.beatrice.dgtuProject.repository;

import org.beatrice.dgtuProject.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
