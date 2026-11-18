package com.codekeys.saas.exceptions;

import com.codekeys.saas.exceptions.responses.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value=BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException exception, final HttpServletRequest servletRequest){
        log.error("Bussiness Error", exception);
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .path(servletRequest.getRequestURI())
                .build();
        HttpStatus status = getHttpStatus(exception);
        return ResponseEntity.status(status)
                .body(errorResponse);
    }



    @ExceptionHandler(value=EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final EntityNotFoundException exception, final HttpServletRequest servletRequest){
        log.error("Entity not found", exception);
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("NOT FOUND")
                .message(exception.getMessage())
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException exception, final HttpServletRequest servletRequest){
        log.error("Validation Error", exception);
        final List<ErrorResponse.ValidationError> validationErrors = new ArrayList<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error ->{
                    final String fieldName = ((FieldError) error).getField();
                    final String errorCode = error.getDefaultMessage();
                    final String defaultMessage = error.getDefaultMessage(); //todo add translation later
                    validationErrors.add(ErrorResponse.ValidationError.builder()
                                    .field(fieldName)
                                    .code(errorCode)
                                    .message(defaultMessage)
                                    .build()
                    );
                });
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .validationErrors(validationErrors)
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    private HttpStatus getHttpStatus(BusinessException exception) {
        if(exception instanceof DuplicateRessourceException){
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
