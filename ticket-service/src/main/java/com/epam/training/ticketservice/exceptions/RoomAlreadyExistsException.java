package com.epam.training.ticketservice.exceptions;

public class RoomAlreadyExistsException extends Exception {
    public RoomAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
