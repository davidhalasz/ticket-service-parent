package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.ServiceException.InCorrectParameterException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(String title, String genre, int runtime) throws InCorrectParameterException {
        try {
            MovieEntity movie = new MovieEntity(title, genre, runtime);
            movieRepository.createMovie(movie);
        } catch (Exception err) {
            if (title.isEmpty() || genre.isEmpty() || runtime < 0) {
                throw new InCorrectParameterException("Incorrect parameter(s)", err);
            } else {
                throw err;
            }
        }
    }

    public List<MovieEntity> getAllMovie() {
        return movieRepository.getAllMovie();
    }

    public MovieEntity updateMovie(String title, String genre, int runtime) {
        MovieEntity getMovie = findMovie(title);
        movieRepository.updateMovie(getMovie.getTitle(), genre, runtime);
        return getMovie;
    }

    public MovieEntity deleteMovie(String title) {
        MovieEntity getMovie = findMovie(title);
        movieRepository.deleteMovie(getMovie.getTitle());
        return getMovie;
    }

    public MovieEntity findMovie(String title) {
        return movieRepository.getAllMovie().stream()
                .filter(currentMovie -> currentMovie.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() ->new IllegalArgumentException("There is no such title"));
    }
}
