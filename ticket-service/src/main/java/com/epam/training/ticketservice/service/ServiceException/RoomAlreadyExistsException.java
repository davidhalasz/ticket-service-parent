package com.epam.training.ticketservice.service.ServiceException;

public class RoomAlreadyExistsException extends Exception{
    public RoomAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
