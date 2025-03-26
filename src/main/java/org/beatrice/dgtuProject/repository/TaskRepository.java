package org.beatrice.dgtuProject.repository;

import org.beatrice.dgtuProject.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserEmail(String userEmail);
}
