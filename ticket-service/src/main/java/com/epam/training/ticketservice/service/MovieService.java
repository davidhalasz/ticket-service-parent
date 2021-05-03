package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieEntity addMovie(String title, String genre, int runtime) {
        MovieEntity movie = new MovieEntity(title, genre, runtime);
        movieRepository.createMovie(movie);
        return movie;
    }
}
