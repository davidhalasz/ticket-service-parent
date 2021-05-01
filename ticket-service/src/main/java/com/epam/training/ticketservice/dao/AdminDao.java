package com.epam.training.ticketservice.dao;

import com.epam.training.ticketservice.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminDao extends JpaRepository<AdminEntity, UUID> {
    AdminEntity findByName(String name);
}
