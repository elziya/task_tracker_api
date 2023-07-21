package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.request.ProjectRequest;
import ru.kpfu.itis.dto.response.ProjectResponse;
import ru.kpfu.itis.dto.response.ProjectsPage;
import ru.kpfu.itis.models.Project;

import java.util.Optional;

public interface ProjectService {

    ProjectResponse addProject(ProjectRequest projectRequest);

    void deleteProject(Long projectId);

    Project checkAccessToProject(Long projectId);

    Optional<Project> getProjectByIdAndState(Long projectId, Project.State state);

    ProjectResponse updateProject(Long projectId, ProjectRequest newData);

    ProjectsPage getProjects(int page);

    ProjectResponse getProject(Long projectId);

    Project updateProjectSimple(Project project);
}
