package com.epam.training.ticketservice.exceptions;

public class ScreeningNotFoundException extends Exception {
    public ScreeningNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
