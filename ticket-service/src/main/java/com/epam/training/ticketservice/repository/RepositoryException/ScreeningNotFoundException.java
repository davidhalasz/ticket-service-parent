package com.epam.training.ticketservice.repository.RepositoryException;

public class ScreeningNotFoundException extends Exception {
    public ScreeningNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
