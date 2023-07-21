package ru.kpfu.itis.controllers.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kpfu.itis.exceptions.ServiceException;
import ru.kpfu.itis.validation.http.ValidationResultDto;
import ru.kpfu.itis.validation.http.ValidationExceptionResponse;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionDto> onMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationExceptionResponse.builder()
                        .errors(formFieldValidationErrors(ex.getFieldErrors()))
                        .message("Invalid data in request")
                        .exceptionName(ex.getClass().getSimpleName())
                        .build()
                );
    }

    private List<ValidationResultDto> formFieldValidationErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(e -> ValidationResultDto.builder()
                        .object(e.getObjectName())
                        .message(e.getDefaultMessage())
                        .field(e.getField())
                        .fieldValue(String.valueOf(e.getRejectedValue()))
                        .build())
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ExceptionDto> onServiceExceptions(ServiceException exception) {

        return ResponseEntity.status(exception.getHttpStatus())
                .body(ExceptionDto.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionDto> onAllExceptions(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build());
    }
}
