package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.ServiceException.InCorrectParameterException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Command handler for 'movie' command
 */

@ShellComponent
public class MovieCommandHandler {

    private MovieService movieService;
    private AdminService adminService;

    public MovieCommandHandler(MovieService movieService, AdminService adminService) {
        this.movieService = movieService;
        this.adminService = adminService;
    }


    @ShellMethod(value = "Create a new movie", key = "create movie")
    public String createMovie(String title, String genre, int runtime) throws InCorrectParameterException {
        if (adminService.loggedAdmin()) {
            movieService.addMovie(title, genre, runtime);
            return "Movie added";
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(value = "List all movie", key = "list movies")
    public String listMovies() {
        StringBuilder builder = new StringBuilder();

        if (movieService.getAllMovie().isEmpty()) {
            return "There are no movies at the moment";
        } else {
            for (MovieEntity value : movieService.getAllMovie()) {
                builder.append(value);
            }
            return builder.toString();
        }
    }

    @ShellMethod(value = "Update a movie", key = "update movie")
    public String updateMovie(String title, String genre, int runtime) {
        if (adminService.loggedAdmin()) {
            MovieEntity movie = movieService.updateMovie(title, genre, runtime);
            return "Movie updated.";
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(value = "Delete a movie.", key = "delete movie")
    public String deleteMovie(String title) {
        if (adminService.loggedAdmin()) {
            MovieEntity movie = movieService.deleteMovie(title);
            return "Movie deleted.";
        } else {
            return "You are not signed in";
        }
    }

}
