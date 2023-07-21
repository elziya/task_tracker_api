package ru.kpfu.itis.exceptions;


import org.springframework.http.HttpStatus;

public class BadRequestException extends ServiceException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.BAD_REQUEST);
    }
}
