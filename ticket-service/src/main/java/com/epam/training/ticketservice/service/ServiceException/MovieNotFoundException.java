package com.epam.training.ticketservice.service.ServiceException;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException (String errorMsg) {
        super(errorMsg);
    }
}
