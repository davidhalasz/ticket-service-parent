package com.epam.training.ticketservice.exceptions;

public class InvalidRuntimeException extends Exception {
    public InvalidRuntimeException(String errorMsg) {
        super(errorMsg);
    }
}
