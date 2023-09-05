package com.rentcars.clients.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DrivingLicenseDoesntExistException extends RuntimeException {
    public DrivingLicenseDoesntExistException(String s) {
        super(s);
    }
}
