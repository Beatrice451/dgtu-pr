package org.beatrice.hui.repository;

import org.beatrice.hui.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByName(String name);

    Optional<User> findByEmail(String email);
}
