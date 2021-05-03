package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;

import java.util.List;

public interface MovieRepository{

    void createMovie(MovieEntity movie);

    List<MovieEntity> getAllMovie();
}
