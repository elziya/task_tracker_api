package ru.kpfu.itis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Project info for creating and updating")
public class ProjectRequest {

    @NotBlank(message = "The name can't be empty")
    @Schema(description = "Name", example = "ML project - neural network")
    private String name;
}
