package com.microservice.auth.repository;

import com.microservice.auth.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<MediaEntity, UUID> {
}
