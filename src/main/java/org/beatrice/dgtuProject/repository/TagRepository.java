package org.beatrice.dgtuProject.repository;

import org.beatrice.dgtuProject.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    Set<Tag> findByNameIn(Collection<String> names);
    
}
