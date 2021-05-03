package com.epam.training.ticketservice.service.ServiceException;

public class InCorrectParameterException extends Exception{
    public InCorrectParameterException(String errorMsg, Throwable err) {
        super(errorMsg);
    }
}
