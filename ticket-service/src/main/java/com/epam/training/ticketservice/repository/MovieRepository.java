package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieRepository {

    void createMovie(Movie movie)
            throws MovieAlreadyExistsException, InvalidRuntimeException;

    List<Movie> getAllMovies();

    void updateMovie(String title, String genre, int runtime)
            throws MovieNotFoundException, InvalidRuntimeException;

    void deleteMovie(String title)
            throws MovieNotFoundException;

    Movie getMovieByTitle(String title)
            throws MovieNotFoundException;

    boolean isMovieExists(String title);
}
