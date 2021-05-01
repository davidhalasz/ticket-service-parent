package com.epam.training.ticketservice.dao;

import com.epam.training.ticketservice.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieDao extends JpaRepository<MovieEntity, UUID> {
    MovieEntity findByTitle(String title);
}
