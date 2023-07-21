package ru.kpfu.itis.exceptions;

import org.springframework.http.HttpStatus;

public class FileException extends ServiceException{

    public FileException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    public FileException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
