package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.request.ProjectRequest;
import ru.kpfu.itis.dto.response.ProjectResponse;
import ru.kpfu.itis.dto.response.ProjectsPage;
import ru.kpfu.itis.exceptions.AccessDeniedException;
import ru.kpfu.itis.exceptions.ProjectNotFoundException;
import ru.kpfu.itis.models.Account;
import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.repositories.ProjectRepository;
import ru.kpfu.itis.security.details.AccountUserDetails;
import ru.kpfu.itis.services.ProjectService;
import ru.kpfu.itis.utils.converters.DateConverter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

import static ru.kpfu.itis.dto.response.ProjectResponse.from;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final DateConverter dateConverter;

    @Value("${tracker.default-page-size}")
    private int defaultPageSize;

    @Override
    public ProjectResponse addProject(ProjectRequest projectRequest) {
        Account account = ((AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAccount();

        Project project = Project.builder()
                .name(projectRequest.getName())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .state(Project.State.CREATED)
                .account(account)
                .build();

        return from(projectRepository.save(project), dateConverter);
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectRequest newData) {
        Project project = this.checkAccessToProject(projectId);

        project.setName(newData.getName());

        return from(projectRepository.save(project), dateConverter);
    }

    @Override
    public ProjectsPage getProjects(int page) {
        AccountUserDetails userDetails = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize, Sort.by("endDate").descending());
        Page<Project> projectPage = projectRepository
                .findAllByAccount_IdAndState(userDetails.getAccount().getId(), Project.State.CREATED, pageRequest);

        return ProjectsPage.builder()
                .projects(from(projectPage.getContent(), dateConverter))
                .totalPages(projectPage.getTotalPages())
                .build();
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        return from(this.checkAccessToProject(projectId), dateConverter);
    }

    @Override
    public Project updateProjectSimple(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = this.checkAccessToProject(projectId);

        project.setState(Project.State.DELETED);
        projectRepository.save(project);
    }

    @Override
    public Project checkAccessToProject(Long projectId) {
        AccountUserDetails userDetails = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Project project = this.getProjectByIdAndState(projectId, Project.State.CREATED)
                .orElseThrow((Supplier<RuntimeException>) () ->
                        new ProjectNotFoundException(projectId));

        if(!project.getAccount().getId().equals(userDetails.getAccount().getId())){
            throw new AccessDeniedException("Access to project was denied");
        }

        return project;
    }

    @Override
    public Optional<Project> getProjectByIdAndState(Long projectId, Project.State state) {
        return projectRepository.getProjectByIdAndState(projectId, state);
    }

}
