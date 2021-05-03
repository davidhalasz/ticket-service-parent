package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.ServiceException.InvalidRuntimeException;
import com.epam.training.ticketservice.service.ServiceException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.MovieNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieEntity addMovie(String title, String genre, int runtime) throws MovieAlreadyExistsException, InvalidRuntimeException {
        MovieEntity movie = new MovieEntity(title, genre, runtime);
        movieRepository.createMovie(movie);
        return movie;
    }

    public List<MovieEntity> getAllMovie() {
        return movieRepository.getAllMovie();
    }

    public MovieEntity updateMovie(String title, String genre, int runtime) throws MovieNotFoundException, InvalidRuntimeException {
        return movieRepository.updateMovie(title, genre, runtime);
    }

    public MovieEntity deleteMovie(String title) throws MovieNotFoundException {
        return movieRepository.deleteMovie(title);
    }

}
