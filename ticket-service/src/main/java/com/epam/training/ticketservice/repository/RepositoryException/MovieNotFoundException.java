package com.epam.training.ticketservice.repository.RepositoryException;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException (String errorMsg) {
        super(errorMsg);
    }
}
