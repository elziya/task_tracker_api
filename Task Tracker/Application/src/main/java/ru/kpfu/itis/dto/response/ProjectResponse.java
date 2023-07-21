package ru.kpfu.itis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.utils.converters.DateConverter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dto for project information")
public class ProjectResponse {

    @Schema(description = "Identifier", example = "1")
    private Long id;

    @Schema(description = "Name", example = "ML project - neural network")
    private String name;

    @Schema(description = "Start date", example = "28 APRIL 2022")
    private String startDate;

    @Schema(description = "End date", example = "22 MAY 2022")
    private String endDate;

    public static ProjectResponse from(Project project, DateConverter dateConverter) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .startDate(dateConverter.convert(project.getStartDate()))
                .endDate(dateConverter.convert(project.getEndDate()))
                .build();
    }

    public static List<ProjectResponse> from(List<Project> projects, DateConverter dateConverter) {
        return projects.stream().map(project -> from(project, dateConverter)).collect(Collectors.toList());
    }
}
