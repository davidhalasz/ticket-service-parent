package com.epam.training.ticketservice.service.ServiceException;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String errorMsg) {
        super(errorMsg);
    }
}
