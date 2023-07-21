package ru.kpfu.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.controllers.handlers.ExceptionDto;
import ru.kpfu.itis.dto.request.ProjectRequest;
import ru.kpfu.itis.dto.response.ProjectResponse;
import ru.kpfu.itis.dto.response.ProjectsPage;
import ru.kpfu.itis.services.ProjectService;
import ru.kpfu.itis.validation.http.ValidationExceptionResponse;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsController {

    private final ProjectService projectService;

    @Operation(summary = "Create project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created project successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProjectResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Project info is invalid",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ValidationExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<ProjectResponse> addProject(@Valid @RequestBody ProjectRequest project){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.addProject(project));
    }

    @Operation(summary = "Get project of account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got project successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProjectResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Access to project was denied",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @GetMapping("/{project-id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("project-id") Long projectId) {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }

    @Operation(summary = "Get projects of account with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got projects successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProjectsPage.class)
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<ProjectsPage> getProjects(@RequestParam("page") int page) {
        return ResponseEntity.ok(projectService.getProjects(page));
    }

    @Operation(summary = "Update project of account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Updated project successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProjectResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Access to project was denied",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @PutMapping("/{project-id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("project-id") Long projectId,
                                                         @Valid @RequestBody ProjectRequest newData) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(projectService.updateProject(projectId, newData));
    }

    @Operation(summary = "Delete project of account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Deleted project successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Access to project was denied",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @DeleteMapping("/{project-id}")
    public ResponseEntity<?> deleteProject(@PathVariable("project-id") Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
