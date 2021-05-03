package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
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

    public void addMovie(String title, String genre, int runtime) throws MovieAlreadyExistsException {
        MovieEntity movie = new MovieEntity(title, genre, runtime);
        movieRepository.createMovie(movie);
    }

    public List<MovieEntity> getAllMovie() {
        return movieRepository.getAllMovie();
    }

    public MovieEntity updateMovie(String title, String genre, int runtime) throws MovieNotFoundException {
        MovieEntity getMovie = findMovie(title);
        movieRepository.updateMovie(getMovie.getTitle(), genre, runtime);
        return getMovie;
    }

    public MovieEntity deleteMovie(String title) throws MovieNotFoundException {
        MovieEntity getMovie = findMovie(title);
        try {
            movieRepository.deleteMovie(getMovie.getTitle());
        } catch (com.epam.training.ticketservice.service.ServiceException.MovieNotFoundException e) {
            e.printStackTrace();
        }
        return getMovie;
    }

    public MovieEntity findMovie(String title) {
        return movieRepository.getAllMovie().stream()
                .filter(currentMovie -> currentMovie.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() ->new IllegalArgumentException("There is no such title"));
    }
}
