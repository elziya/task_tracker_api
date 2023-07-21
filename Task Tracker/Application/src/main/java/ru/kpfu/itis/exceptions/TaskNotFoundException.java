package ru.kpfu.itis.exceptions;

public class TaskNotFoundException extends NotFoundException {

    public TaskNotFoundException(Long taskId) {
        super("Task with id <" + taskId + "> not found");
    }
}
