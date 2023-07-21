package ru.kpfu.itis.exceptions;

import org.springframework.http.HttpStatus;

public class NotAllowedMethodException extends ServiceException {

    public NotAllowedMethodException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    public NotAllowedMethodException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
