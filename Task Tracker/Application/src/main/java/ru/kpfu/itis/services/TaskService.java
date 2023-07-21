package ru.kpfu.itis.services;

import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.dto.response.TasksPage;

public interface TaskService {

    TaskResponse addTask(TaskRequest taskRequest, Long projectId);

    TaskResponse getTask(Long projectId, Long taskId);

    void uploadFile(MultipartFile[] multipartFiles, Long taskId, Long projectId);

    TasksPage getTasks(int page, Long projectId);
}
