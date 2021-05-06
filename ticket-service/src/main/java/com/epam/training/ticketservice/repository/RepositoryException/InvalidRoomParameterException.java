package com.epam.training.ticketservice.repository.RepositoryException;

public class InvalidRoomParameterException extends Exception {
    public InvalidRoomParameterException(String errorMsg) {
        super(errorMsg);
    }
}
