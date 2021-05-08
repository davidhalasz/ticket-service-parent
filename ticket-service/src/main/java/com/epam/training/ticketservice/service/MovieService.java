package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.exceptions.DeleteException;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    public MovieService(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
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

    public void deleteMovie(String title) throws MovieNotFoundException, DeleteException {
        List<Screening> screenings = screeningRepository.getAllScreenings();
        Screening screening = screenings.stream()
                .filter(currentScreening -> currentScreening.getMovie().getTitle().equals(title))
                .findFirst()
                .orElse(null);
        if(screening != null) {
            throw new DeleteException("You cannot delete this movie because there is a screening with title like this.");
        }
        movieRepository.deleteMovie(title);
    }
}
