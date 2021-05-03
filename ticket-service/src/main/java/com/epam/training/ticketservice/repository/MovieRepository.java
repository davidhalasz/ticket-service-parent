package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;

public interface MovieRepository{

    void createMovie(MovieEntity movie);
}
