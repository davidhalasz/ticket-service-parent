package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.service.ServiceException.InvalidRuntimeException;
import com.epam.training.ticketservice.service.ServiceException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.MovieNotFoundException;

import java.util.List;

public interface MovieRepository{

    void createMovie(Movie movie) throws MovieAlreadyExistsException, InvalidRuntimeException;

    List<Movie> getAllMovie();

    Movie updateMovie(String title, String genre, int runtime) throws MovieNotFoundException, InvalidRuntimeException;

    Movie deleteMovie(String title) throws MovieNotFoundException;

    Movie findMovieByTitle(String title);
}
