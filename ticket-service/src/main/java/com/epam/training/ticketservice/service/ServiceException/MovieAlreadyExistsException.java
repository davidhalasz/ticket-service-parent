package com.epam.training.ticketservice.service.ServiceException;

public class MovieAlreadyExistsException extends Exception{
    public MovieAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
