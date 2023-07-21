package ru.kpfu.itis.validation.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Validation result")
public class ValidationResultDto {

    @Schema(description = "Invalid field", example = "email")
    private String field;

    @Schema(description = "Invalid object", example = "signUpForm")
    private String object;

    @Schema(description = "Message", example = "The email can't be empty")
    private String message;

    @Schema(description = "Invalid field value", example = "qwerty")
    private String fieldValue;
}
