package com.epam.training.ticketservice.repository.RepositoryException;

public class AdminIsNotLoggedInException extends Exception {
    public AdminIsNotLoggedInException(String errorMsg) {
        super(errorMsg);
    }
}
