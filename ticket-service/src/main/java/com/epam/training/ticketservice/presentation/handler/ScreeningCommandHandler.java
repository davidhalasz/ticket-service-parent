package com.epam.training.ticketservice.presentation.handler;


import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.RepositoryException.*;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Command handler for 'screening' command
 */

@ShellComponent
public class ScreeningCommandHandler {

    private ScreeningService screeningService;
    private AdminService adminService;

    public ScreeningCommandHandler(ScreeningService screeningService, AdminService adminService) {
        this.screeningService = screeningService;
        this.adminService = adminService;
    }

    @ShellMethod(value = "Create a new screening", key = "create screening")
    public String createScreening(String movieTitle, String roomName, String startDateTime)
            throws OverlappingException, MovieNotFoundException, RoomNotFoundException, OverlappingInBreakException{
        String result;
        try {
            if (adminService.loggedAdmin()) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                screeningService.createScreening(movieTitle, roomName, LocalDateTime.parse(startDateTime, dateTimeFormatter));
                result = "Screening added";
            } else {
                result = "You are not signed in";
            }
        } catch (OverlappingException | RoomNotFoundException | MovieNotFoundException | OverlappingInBreakException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "Delete a new screening", key = "delete screening")
    public String deleteScreening(String movieTitle, String roomName, String startDateTime) throws ScreeningNotFoundException {
        String result;
        try {
            if (adminService.loggedAdmin()) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                screeningService.deleteScreening(movieTitle, roomName, LocalDateTime.parse(startDateTime, dateTimeFormatter));
                result = "Screening deleted";
            } else {
                result = "You are not signed in";
            }
        } catch (ScreeningNotFoundException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "List all new screening", key = "list screenings")
    public String listScreenings()  {
        StringBuilder builder = new StringBuilder();
        if (screeningService.getAllScreening().isEmpty()) {
            return "There are no screenings";
        } else {
            for (Screening screening : screeningService.getAllScreening()) {
                builder.append(screening);
            }
            return builder.toString();
        }
    }
}
