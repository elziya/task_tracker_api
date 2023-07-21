package ru.kpfu.itis.exceptions;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(Long projectId) {
        super("Project with id <" + projectId + "> not found");
    }
}
