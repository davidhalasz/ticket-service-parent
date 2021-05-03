package com.epam.training.ticketservice.presentation.handler;


import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Command handler for 'room' command
 */

@ShellComponent
@Slf4j
public class RoomCommandHandler {

    private RoomService roomService;
    private AdminService adminService;

    public RoomCommandHandler(RoomService roomService, AdminService adminService) {
        this.roomService = roomService;
        this.adminService = adminService;
    }

    @ShellMethod(value = "Create a new room", key = "create room")
    public String createRoom(String name, int rows, int columns) throws RoomAlreadyExistsException {
        String result;
        try {
            if (adminService.loggedAdmin()) {
                RoomEntity room = roomService.createRoom(name, rows, columns);
                result = "Room added";
            } else {
                result = "You are not signed in";
            }
        } catch (RoomAlreadyExistsException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "Update a room", key = "update room")
    public String updateRoom(String name, int rows, int columns) throws RoomNotFoundException {
        String result;
        try {
            if (adminService.loggedAdmin()) {
                RoomEntity roomEntity = roomService.updateRoom(name, rows, columns);
                result = "Room updated";
            } else {
                result = "You are not signed in";
            }
        } catch (RoomNotFoundException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "Delete a room", key = "delete room")
    public String deleteRoom(String name) throws RoomNotFoundException{
        String result;
        try {
            if (adminService.loggedAdmin()) {
                RoomEntity roomEntity = roomService.deleteRoom(name);
                result = "Room deleted";
            } else {
                result = "You are not signed in";
            }
        } catch (RoomNotFoundException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "List all room", key = "list rooms")
    public String listRooms() {
        StringBuilder builder = new StringBuilder();
        if (roomService.getAllRoom().isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            for (RoomEntity room : roomService.getAllRoom()) {
                builder.append(room);
            }
            return builder.toString();
        }
    }
}
