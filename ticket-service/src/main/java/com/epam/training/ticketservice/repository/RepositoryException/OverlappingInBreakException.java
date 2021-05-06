package com.epam.training.ticketservice.repository.RepositoryException;

public class OverlappingInBreakException extends Exception {
    public OverlappingInBreakException(String errorMsg) {
        super(errorMsg);
    }
}
