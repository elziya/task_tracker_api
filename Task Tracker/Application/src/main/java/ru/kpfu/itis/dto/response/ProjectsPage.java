package ru.kpfu.itis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dto for page with projects")
public class ProjectsPage {

    @Schema(description = "Projects on current page")
    private List<ProjectResponse> projects;

    @Schema(description = "Total amount of available pages with projects", example = "5")
    private Integer totalPages;
}
