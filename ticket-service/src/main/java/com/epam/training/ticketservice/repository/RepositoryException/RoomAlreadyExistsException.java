package com.epam.training.ticketservice.repository.RepositoryException;

public class RoomAlreadyExistsException extends Exception{
    public RoomAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
