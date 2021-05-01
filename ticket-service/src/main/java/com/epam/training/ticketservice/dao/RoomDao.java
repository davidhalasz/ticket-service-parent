package com.epam.training.ticketservice.dao;

import com.epam.training.ticketservice.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomDao extends JpaRepository<RoomEntity, UUID> {
    RoomEntity findByName(String name);
}
