package com.epam.training.ticketservice.exceptions;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
