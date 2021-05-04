package com.epam.training.ticketservice.presentation.handler;


import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.ServiceException.OverlappingException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDate;
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
    public String createScreening(String movieTitle, String roomName, String startDateTime) throws OverlappingException {
        String result;
        if (adminService.loggedAdmin()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            screeningService.createScreening(movieTitle, roomName, LocalDateTime.parse(startDateTime, dateTimeFormatter));
            result = "Screening added";
        } else {
            result = "You are not signed in";
        }
        return result;
    }


}
