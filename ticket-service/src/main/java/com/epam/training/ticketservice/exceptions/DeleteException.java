package com.epam.training.ticketservice.exceptions;

public class DeleteException extends Exception {
    public DeleteException(String errorMsg) {
        super(errorMsg);
    }
}
