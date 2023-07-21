package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.ProjectResponse;
import ru.kpfu.itis.dto.response.ProjectsPage;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.dto.response.TasksPage;
import ru.kpfu.itis.exceptions.TaskNotFoundException;
import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.TaskRepository;
import ru.kpfu.itis.security.details.AccountUserDetails;
import ru.kpfu.itis.services.FileService;
import ru.kpfu.itis.services.ProjectService;
import ru.kpfu.itis.services.TaskService;
import ru.kpfu.itis.utils.converters.DateConverter;

import java.time.LocalDate;
import java.util.function.Supplier;

import static ru.kpfu.itis.dto.response.TaskResponse.from;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectService projectService;
    private final FileService fileService;

    private final DateConverter dateConverter;

    @Value("${tracker.default-page-size}")
    private int defaultPageSize;

    @Override
    public TaskResponse addTask(TaskRequest taskRequest, Long projectId) {
        Project project = projectService.checkAccessToProject(projectId);
        project.setEndDate(LocalDate.now());

        Task task = Task.builder()
                .name(taskRequest.getName())
                .duration(taskRequest.getDuration())
                .creationDate(LocalDate.now())
                .project(projectService.updateProjectSimple(project))
                .build();

        taskRepository.save(task);

        return from(taskRepository.save(task), dateConverter);
    }

    @Override
    public TaskResponse getTask(Long projectId, Long taskId) {
        projectService.checkAccessToProject(projectId);

        return TaskResponse.from(taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow((Supplier<RuntimeException>) () ->
                        new TaskNotFoundException(taskId)), dateConverter);
    }

    @Override
    public void uploadFile(MultipartFile[] multipartFiles, Long taskId, Long projectId) {

        projectService.checkAccessToProject(projectId);

        for (MultipartFile multipartFile : multipartFiles) {
            fileService.uploadFile(multipartFile, taskRepository.findByIdAndProject_Id(taskId, projectId)
                    .orElseThrow((Supplier<RuntimeException>) () ->
                            new TaskNotFoundException(taskId)));
        }
    }

    @Override
    public TasksPage getTasks(int page, Long projectId) {
        AccountUserDetails userDetails = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("creationDate").descending());

        Page<Task> tasksPage = taskRepository
                .findAllByProject_Account_IdAndProject_StateAndProject_Id(userDetails.getAccount().getId(),
                        Project.State.CREATED, projectId, pageRequest);

        return TasksPage.builder()
                .tasks(from(tasksPage.getContent(), dateConverter))
                .totalPages(tasksPage.getTotalPages())
                .build();
    }
}
