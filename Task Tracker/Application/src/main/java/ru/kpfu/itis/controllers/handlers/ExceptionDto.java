package ru.kpfu.itis.controllers.handlers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema(description = "Dto for exception message")
public class ExceptionDto {

    @Schema(description = "Message", example = "Project with id <5> not found")
    private String message;

    @Schema(description = "Exception name", example = "ProjectNotFoundException")
    private String exceptionName;
}

