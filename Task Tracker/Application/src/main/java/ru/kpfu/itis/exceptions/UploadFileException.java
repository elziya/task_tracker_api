package ru.kpfu.itis.exceptions;

public class UploadFileException extends FileException {

    public UploadFileException() {
        super("Can't upload file");
    }
}
