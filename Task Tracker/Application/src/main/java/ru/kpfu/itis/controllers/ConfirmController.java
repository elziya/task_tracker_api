package ru.kpfu.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.controllers.handlers.ExceptionDto;
import ru.kpfu.itis.services.SignUpService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    private final SignUpService signUpService;

    @Operation(summary = "Account email confirmation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmed account successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = Boolean.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Illegal confirm code or already confirmed account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    @GetMapping("/{confirm-code}")
    public ResponseEntity<Boolean> confirmAccount(@PathVariable("confirm-code") String code){
        return ResponseEntity.ok(signUpService.confirmEmail(code));
    }
}

