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
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.controllers.handlers.ExceptionDto;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.ProjectResponse;
import ru.kpfu.itis.dto.response.ProjectsPage;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.dto.response.TasksPage;
import ru.kpfu.itis.services.TaskService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects/{project-id}/tasks")
public class TasksController {

    private final TaskService taskService;

    @Operation(summary = "Create task of account project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created task of project successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = TaskResponse.class)
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
    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@Valid @RequestBody TaskRequest task,
                                                @PathVariable("project-id") Long projectId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.addTask(task, projectId));
    }

    @Operation(summary = "Get task of account project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got project successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = TaskResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Project not found or task not found",
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
    @GetMapping(path = "/{task-id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable("project-id") Long projectId,
                                                @PathVariable("task-id") Long taskId){
        return ResponseEntity.ok(taskService.getTask(projectId, taskId));
    }

    @Operation(summary = "Upload files to task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Uploaded files to task successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "405", description = "Unable to upload files",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/{task-id}/files")
    public void uploadFileToTask(@RequestParam("files") MultipartFile[] multipartFiles,
                                 @PathVariable("task-id") Long taskId,
                                 @PathVariable("project-id") Long projectId){
        taskService.uploadFile(multipartFiles, taskId, projectId);
    }

    @Operation(summary = "Get tasks of account project with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got tasks successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = TasksPage.class)
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<TasksPage> getTasks(@PathVariable("project-id") Long projectId,
                                              @RequestParam("page") int page) {
        return ResponseEntity.ok(taskService.getTasks(page, projectId));
    }
}
