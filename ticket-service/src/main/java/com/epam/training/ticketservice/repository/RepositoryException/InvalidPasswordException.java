package com.epam.training.ticketservice.repository.RepositoryException;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String errorMsg) {
        super(errorMsg);
    }
}
