package ru.kpfu.itis.exceptions;

public class SendingEmailException extends NotAllowedMethodException {

    public SendingEmailException() {
        super("Can't send email");
    }
}
