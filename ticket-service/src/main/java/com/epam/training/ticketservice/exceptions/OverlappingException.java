package com.epam.training.ticketservice.exceptions;

public class OverlappingException extends Exception {
    public OverlappingException(String errorMsg) {
        super(errorMsg);
    }
}
