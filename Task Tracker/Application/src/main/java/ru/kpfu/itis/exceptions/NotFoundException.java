package ru.kpfu.itis.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.NOT_FOUND);
    }
}
