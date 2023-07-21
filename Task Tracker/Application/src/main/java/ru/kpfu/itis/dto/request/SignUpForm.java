package ru.kpfu.itis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kpfu.itis.validation.annotations.NotSameNames;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Sign up form")
@NotSameNames(names = {"firstName", "lastName"}, message = "{names} are same")
public class SignUpForm {

    @Email(message = "The email must have an email format")
    @NotBlank(message = "The email can't be empty")
    @Schema(description = "Email", example = "qwerty007Q")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,50}$", message = "The password must match the {regexp} pattern")
    @Size(min = 8, max = 50, message = "The password must contains at least {min} characters, " +
            "as a maximum {max} characters")
    @NotBlank(message = "The password can't be empty")
    @Schema(description = "Password", example = "qwerty007Q")
    private String password;

    @Size(min = 2, max = 100, message = "Minimum length of first name: {min}, maximum: {max}")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z-\\s]{2,100}$", message = "First name must match the {regexp} pattern")
    @NotBlank(message = "The first name can't be empty")
    @Schema(description = "First name", example = "Polina")
    private String firstName;

    @Size(min = 2, max = 100, message = "Minimum length of last name: {min}, maximum: {max}")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z-\\s]{2,100}$", message = "Last name must match the {regexp} pattern")
    @NotBlank(message = "The last name can't be empty")
    @Schema(description = "Last name", example = "Brigadirenko")
    private String lastName;
}
