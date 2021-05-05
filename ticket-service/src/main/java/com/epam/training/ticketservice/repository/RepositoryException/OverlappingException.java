package com.epam.training.ticketservice.repository.RepositoryException;

public class OverlappingException extends Exception {
    public OverlappingException(String errorMsg) {
        super(errorMsg);
    }
}
