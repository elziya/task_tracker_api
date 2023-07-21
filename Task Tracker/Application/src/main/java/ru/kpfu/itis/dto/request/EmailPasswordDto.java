package ru.kpfu.itis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dto for login")
public class EmailPasswordDto {

    @Schema(description = "Email", example = "qwerty007Q")
    private String email;

    @Schema(description = "Password", example = "qwerty007Q")
    private String password;
}
