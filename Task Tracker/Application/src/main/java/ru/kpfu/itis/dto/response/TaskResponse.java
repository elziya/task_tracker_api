package ru.kpfu.itis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.utils.converters.DateConverter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dto for task information")
public class TaskResponse {

    @Schema(description = "Identifier", example = "1")
    private Long id;

    @Schema(description = "Name", example = "Find dataset")
    private String name;

    @Schema(description = "Duration in minutes", example = "65")
    private Integer duration;

    @Schema(description = "Creation date", example = "28 APRIL 2022")
    private String creationDate;

    public static TaskResponse from(Task task, DateConverter dateConverter) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .creationDate(dateConverter.convert(task.getCreationDate()))
                .duration(task.getDuration())
                .build();
    }

    public static List<TaskResponse> from(List<Task> tasks, DateConverter dateConverter) {
        return tasks.stream().map(task -> from(task, dateConverter)).collect(Collectors.toList());
    }
}
