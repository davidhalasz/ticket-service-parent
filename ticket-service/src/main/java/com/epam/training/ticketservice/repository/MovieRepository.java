package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRuntimeException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieNotFoundException;

import java.util.List;

public interface MovieRepository{

    void createMovie(Movie movie) throws MovieAlreadyExistsException, InvalidRuntimeException;

    List<Movie> getAllMovie();

    Movie updateMovie(String title, String genre, int runtime) throws MovieNotFoundException, InvalidRuntimeException;

    Movie deleteMovie(String title) throws MovieNotFoundException;

    Movie findMovieByTitle(String title) throws MovieNotFoundException;
}
