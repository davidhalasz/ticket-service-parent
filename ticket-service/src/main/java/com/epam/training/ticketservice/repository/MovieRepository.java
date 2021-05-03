package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.service.ServiceException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.MovieNotFoundException;

import java.util.List;

public interface MovieRepository{

    void createMovie(MovieEntity movie) throws MovieAlreadyExistsException;

    List<MovieEntity> getAllMovie();

    void updateMovie(String title, String genre, int runtime) throws MovieNotFoundException;

    void deleteMovie(String title) throws MovieNotFoundException;
}
