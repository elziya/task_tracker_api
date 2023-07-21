package ru.kpfu.itis.exceptions;

public class DownloadFileException extends FileException{

    public DownloadFileException() {
        super("Can't get file");
    }
}
