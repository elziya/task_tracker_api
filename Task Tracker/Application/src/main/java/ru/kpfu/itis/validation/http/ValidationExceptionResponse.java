package ru.kpfu.itis.validation.http;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kpfu.itis.controllers.handlers.ExceptionDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "All validation exceptions")
public class ValidationExceptionResponse extends ExceptionDto {

    private List<ValidationResultDto> errors;
}
