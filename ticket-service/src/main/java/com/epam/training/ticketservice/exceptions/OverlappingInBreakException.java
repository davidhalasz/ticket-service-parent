package com.epam.training.ticketservice.exceptions;

public class OverlappingInBreakException extends Exception {
    public OverlappingInBreakException(String errorMsg) {
        super(errorMsg);
    }
}
