package com.epam.training.ticketservice.exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String errorMsg) {
        super(errorMsg);
    }
}
