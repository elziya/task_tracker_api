package ru.kpfu.itis.exceptions;

public class AccountIllegalStateException extends BadRequestException {

    public AccountIllegalStateException() {
        super("Account has already been confirmed");
    }
}
