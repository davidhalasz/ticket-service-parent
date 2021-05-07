package com.epam.training.ticketservice.exceptions;

public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
