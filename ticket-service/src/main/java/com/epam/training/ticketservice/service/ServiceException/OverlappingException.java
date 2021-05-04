package com.epam.training.ticketservice.service.ServiceException;

public class OverlappingException extends Exception {
    public OverlappingException(String errorMsg) {
        super(errorMsg);
    }
}
