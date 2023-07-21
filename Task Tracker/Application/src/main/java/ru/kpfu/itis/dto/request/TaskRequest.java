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
@Schema(description = "Task info for creating")
public class TaskRequest {

    @NotBlank(message = "The name can't be empty")
    @Schema(description = "Name", example = "Find dataset")
    private String name;

    @Schema(description = "Duration in minutes", example = "65")
    private Integer duration;
}
