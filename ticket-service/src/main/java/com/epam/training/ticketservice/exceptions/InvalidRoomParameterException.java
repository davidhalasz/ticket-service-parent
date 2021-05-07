package com.epam.training.ticketservice.exceptions;

public class InvalidRoomParameterException extends Exception {
    public InvalidRoomParameterException(String errorMsg) {
        super(errorMsg);
    }
}
