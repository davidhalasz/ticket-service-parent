package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieDao extends JpaRepository<MovieEntity, String> {
    MovieEntity findMovieByTitle(String title);
}
