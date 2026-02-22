package com.fakesmtp.api.repository;

import com.fakesmtp.api.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for ApplicationEntity.
 * @author Gilberto Vazquez
 */
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, UUID> {
}
