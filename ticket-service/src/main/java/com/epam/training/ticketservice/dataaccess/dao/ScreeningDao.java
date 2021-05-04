package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScreeningDao extends JpaRepository<ScreeningEntity, UUID> {
    Optional<ScreeningEntity> findById(UUID uuid);
}
