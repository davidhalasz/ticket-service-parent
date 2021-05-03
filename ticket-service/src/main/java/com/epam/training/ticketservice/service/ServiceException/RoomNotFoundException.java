package com.epam.training.ticketservice.service.ServiceException;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
