package ru.kpfu.itis.exceptions;

public class DuplicateEmailException extends BadRequestException {

    public DuplicateEmailException() {
        super("The account with this email has been already registered");
    }
}
