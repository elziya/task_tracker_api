package ru.kpfu.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.controllers.handlers.ExceptionDto;
import ru.kpfu.itis.services.FileService;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
public class FilesController {

    private final FileService fileService;

    @Operation(summary = "Get file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got file successfully"),
            @ApiResponse(responseCode = "404", description = "File not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "405", description = "Unable to get file",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{file-id}")
    public void getFile(@PathVariable("file-id") Long fileId, HttpServletResponse response,
                        @AuthenticationPrincipal UserDetails userDetails){
        fileService.addFileToResponse(fileId, response, userDetails);
    }
}
