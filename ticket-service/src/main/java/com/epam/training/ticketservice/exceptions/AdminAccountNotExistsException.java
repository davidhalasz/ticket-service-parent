package com.epam.training.ticketservice.exceptions;

public class AdminAccountNotExistsException extends Exception {
    public AdminAccountNotExistsException(String errorMsg) {
        super(errorMsg);
    }
}
