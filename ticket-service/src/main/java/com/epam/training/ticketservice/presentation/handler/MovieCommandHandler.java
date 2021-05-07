package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
@Slf4j
public class MovieCommandHandler {

    private final MovieService movieService;
    private final AdminService adminService;

    public MovieCommandHandler(MovieService movieService, AdminService adminService) {
        this.movieService = movieService;
        this.adminService = adminService;
    }


    @ShellMethod(value = "Create a new movie", key = "create movie")
    public String createMovie(String title, String genre, int runtime)
            throws MovieAlreadyExistsException, InvalidRuntimeException {

        String result;
        try {
            if (adminService.loggedAdmin()) {
                movieService.addMovie(title, genre, runtime);
                result = "Movie added";
            } else {
                result = "You are not signed in";
            }
        } catch (MovieAlreadyExistsException | InvalidRuntimeException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "List all movie", key = "list movies")
    public String listMovies() {
        StringBuilder builder = new StringBuilder();

        if (movieService.getAllMovies().isEmpty()) {
            return "There are no movies at the moment";
        } else {
            for (Movie value : movieService.getAllMovies()) {
                builder.append(value);
            }
            return builder.toString();
        }
    }

    @ShellMethod(value = "Update a movie", key = "update movie")
    public String updateMovie(String title, String genre, int runtime)
            throws MovieNotFoundException, InvalidRuntimeException {

        String result;
        try {
            if (adminService.loggedAdmin()) {
                movieService.updateMovie(title, genre, runtime);
                result = "Movie updated";
            } else {
                result = "You are not signed in";
            }
        } catch (MovieNotFoundException | InvalidRuntimeException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "Delete a movie.", key = "delete movie")
    public String deleteMovie(String title)
            throws MovieNotFoundException {

        String result;
        try {
            if (adminService.loggedAdmin()) {
                movieService.deleteMovie(title);
                result = "Movie deleted";
            } else {
                result = "You are not signed in";
            }
        } catch (MovieNotFoundException e) {
            result = e.getMessage();
        }
        return result;
    }
}
