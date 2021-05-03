package com.epam.training.ticketservice.service.ServiceException;

public class InvalidRuntimeException extends Exception{
    public InvalidRuntimeException (String errorMsg) {
        super(errorMsg);
    }
}
