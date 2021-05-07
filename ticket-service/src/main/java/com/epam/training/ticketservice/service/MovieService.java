package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(String title, String genre, int runtime)
            throws MovieAlreadyExistsException, InvalidRuntimeException {

        Movie movie = new Movie(title, genre, runtime);
        movieRepository.createMovie(movie);
        return movie;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public void updateMovie(String title, String genre, int runtime)
            throws MovieNotFoundException, InvalidRuntimeException {

        movieRepository.updateMovie(title, genre, runtime);
    }

    public void deleteMovie(String title) throws MovieNotFoundException {
        movieRepository.deleteMovie(title);
    }

}
