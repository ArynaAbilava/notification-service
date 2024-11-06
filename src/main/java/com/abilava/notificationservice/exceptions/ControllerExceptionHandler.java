package com.abilava.notificationservice.exceptions;

import com.abilava.notificationservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, UserExistsException.class})
    public ResponseEntity<ExceptionDto> handleBadRequestException(RuntimeException ex) {
        return handleException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            InvalidCredentialsException.class,
            TokenExpiredException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ExceptionDto> handleUnauthorizedException(RuntimeException ex) {
        return handleException(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleException(RuntimeException ex) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ExceptionDto> handleException(HttpStatus status, String exceptionCode) {
        return ResponseEntity.status(status).body(new ExceptionDto(exceptionCode));
    }

}
