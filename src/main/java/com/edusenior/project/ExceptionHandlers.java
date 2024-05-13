package com.edusenior.project;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Exceptions.EmailNotFoundException;
import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.Exceptions.StudentEmailsNotFoundException;
import com.edusenior.project.ServerResponses.EmailsNotFoundResponse;
import com.edusenior.project.ServerResponses.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

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
        errors.add(ex.getMessage());
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ServerResponse> handleDuplicateEntryException(DuplicateEntryException ex){
        ArrayList<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ServerResponse> handleEmailNotFoundException(EmailNotFoundException ex){
        ArrayList<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StudentEmailsNotFoundException.class)
    public ResponseEntity<ServerResponse> handleStudentEmailsNotFoundException(StudentEmailsNotFoundException ex){
        ArrayList<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        EmailsNotFoundResponse errorResponse = new EmailsNotFoundResponse("failed",errors,ex.getNotFoundEmails());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ServerResponse> handleInvalidOperationException(InvalidOperationException ex){
        ArrayList<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ServerResponse> handleInvalidOperationException(IllegalArgumentException ex){
        ArrayList<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
