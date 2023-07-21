package ru.kpfu.itis.exceptions;

public class FileNotFoundException extends NotFoundException{

    public FileNotFoundException() {
        super("File not found");
    }
}
