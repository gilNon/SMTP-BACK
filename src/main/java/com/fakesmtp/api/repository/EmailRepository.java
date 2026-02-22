package com.fakesmtp.api.repository;

import com.fakesmtp.api.model.ApplicationEntity;
import com.fakesmtp.api.model.EmailEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing EmailEntity persistence.
 * Extends JpaRepository to provide CRUD operations and more.
 * @author Gilberto Vazquez
 */
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {

    @NotNull
    Page<EmailEntity> findAll(Pageable pageable);

    @NotNull
    Page<EmailEntity> findAllByApplication(ApplicationEntity application, Pageable pageable);

    @NotNull
    @EntityGraph(attributePaths = {"attachments"})
    Optional<EmailEntity> findById(UUID id);

    Optional<EmailEntity> findByIdEmailAndApplication(UUID id, ApplicationEntity application);

}
