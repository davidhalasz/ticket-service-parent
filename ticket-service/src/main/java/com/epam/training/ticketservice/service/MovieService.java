package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRuntimeException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(String title, String genre, int runtime) throws MovieAlreadyExistsException, InvalidRuntimeException {
        Movie movie = new Movie(title, genre, runtime);
        movieRepository.createMovie(movie);
        return movie;
    }

    public List<Movie> getAllMovie() {
        return movieRepository.getAllMovie();
    }

    public Movie updateMovie(String title, String genre, int runtime) throws MovieNotFoundException, InvalidRuntimeException {
        return movieRepository.updateMovie(title, genre, runtime);
    }

    public Movie deleteMovie(String title) throws MovieNotFoundException {
        return movieRepository.deleteMovie(title);
    }

}
