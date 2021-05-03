package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.MovieService;
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
    public String createMovie(String title, String genre, int runtime) {
        if (adminService.loggedAdmin()) {
            MovieEntity movie = movieService.addMovie(title, genre, runtime);
            return "Movie added";
        } else {
            return "You are not signed in";
        }
    }

}