package com.fakesmtp.api.repository;

import com.fakesmtp.api.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * User repository.
 * @author Gilberto Vazquez
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Find a user by email.
     * @param email user email.
     * @return user entity.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Find if a user exists by email.
     * @param email user email.
     * @return true if user exists, false otherwise.
     */
    boolean existsByEmail(String email);

}
