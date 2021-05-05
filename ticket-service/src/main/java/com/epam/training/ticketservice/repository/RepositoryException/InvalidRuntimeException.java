package com.epam.training.ticketservice.repository.RepositoryException;

public class InvalidRuntimeException extends Exception{
    public InvalidRuntimeException (String errorMsg) {
        super(errorMsg);
    }
}
