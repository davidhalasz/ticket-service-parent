package com.epam.training.ticketservice.repository.RepositoryException;

public class AdminAccountNotExistsException extends Exception {
    public AdminAccountNotExistsException(String errorMsg) {
        super(errorMsg);
    }
}
