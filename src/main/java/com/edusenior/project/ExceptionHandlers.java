package com.edusenior.project;

import com.edusenior.project.RestControllers.LoginController;
import com.edusenior.project.RestControllers.Student.StudentCustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StudentCustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extracting error messages
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        // Joining messages if multiple, or get the single one
        String combinedErrorMessage = String.join(",", errorMessages);

        // Now using the combinedErrorMessage as the message for the response
        StudentCustomErrorResponse error = new StudentCustomErrorResponse(System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                combinedErrorMessage);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<StudentCustomErrorResponse> handleLoginException(LoginException ex) {
        StudentCustomErrorResponse error = new StudentCustomErrorResponse(System.currentTimeMillis(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
