package io.github.piponsio.smartfinances_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.piponsio.smartfinances_api.utils.CustomResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
            .data(null)
            .message("Invalid email or password")
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomResponse<Void>> handleRuntimeException(RuntimeException ex) {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
            .data(null)
            .message(ex.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Void>> handleGenericException(Exception ex) {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
            .data(null)
            .message("An unexpected error occurred")
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
