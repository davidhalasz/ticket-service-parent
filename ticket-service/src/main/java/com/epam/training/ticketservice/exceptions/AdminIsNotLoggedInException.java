package com.epam.training.ticketservice.exceptions;

public class AdminIsNotLoggedInException extends Exception {
    public AdminIsNotLoggedInException(String errorMsg) {
        super(errorMsg);
    }
}
