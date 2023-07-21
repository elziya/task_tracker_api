package ru.kpfu.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.controllers.handlers.ExceptionDto;
import ru.kpfu.itis.dto.request.SignUpForm;
import ru.kpfu.itis.dto.response.AccountResponse;
import ru.kpfu.itis.services.SignUpService;
import ru.kpfu.itis.validation.http.ValidationExceptionResponse;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignUpController {

    private final SignUpService signUpService;

    @Operation(summary = "Signing up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AccountResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "SignUpForm is invalid",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ValidationExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "405", description = "Can't send email for account confirmation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @PostMapping("/sign-up")
    public ResponseEntity<AccountResponse> signUp(@RequestBody @Valid SignUpForm signUpForm){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpService.signUp(signUpForm));
    }

}
