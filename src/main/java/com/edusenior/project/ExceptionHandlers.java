package com.edusenior.project;

import com.edusenior.project.RestControllers.LoginController;
import com.edusenior.project.RestControllers.Student.StudentCustomErrorResponse;
import com.edusenior.project.Utility.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extracting error messages
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ServerResponse error = new ServerResponse("failed",errorMessages);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ServerResponse> handleLoginException(LoginException ex) {
        ArrayList<String> errors = new ArrayList<>();
        errors.add("Invalid credentials");
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
