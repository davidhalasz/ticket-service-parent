package com.epam.training.ticketservice.repository.RepositoryException;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
