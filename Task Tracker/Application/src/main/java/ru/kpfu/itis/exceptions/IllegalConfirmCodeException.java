package ru.kpfu.itis.exceptions;

public class IllegalConfirmCodeException extends BadRequestException {

    public IllegalConfirmCodeException() {
        super("Illegal confirmation code");
    }
}
