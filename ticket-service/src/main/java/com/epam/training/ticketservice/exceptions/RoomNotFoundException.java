package com.epam.training.ticketservice.exceptions;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
