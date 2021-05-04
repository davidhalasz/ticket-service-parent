package com.epam.training.ticketservice.service.ServiceException;

public class AdminAccountNotExistsException extends Exception {
    public AdminAccountNotExistsException(String errorMsg) {
        super(errorMsg);
    }
}
