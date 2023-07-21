package ru.kpfu.itis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Account info")
public class AccountResponse {

    @Schema(description = "Identifier", example = "1")
    private Long id;

    @Schema(description = "First name", example = "Polina")
    private String firstName;

    @Schema(description = "Last name", example = "Brigadirenko")
    private String lastName;

    @Schema(description = "Email", example = "qwerty007Q")
    private String email;

    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .build();
    }
}
