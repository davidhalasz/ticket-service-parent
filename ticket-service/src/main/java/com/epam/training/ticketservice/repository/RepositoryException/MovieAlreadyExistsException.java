package com.epam.training.ticketservice.repository.RepositoryException;

public class MovieAlreadyExistsException extends Exception{
    public MovieAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
