package com.edusenior.project.RestControllers.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;


@ControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<StudentCustomErrorResponse> StudentNotFoundHandler(StudentNotFoundException ex){
        StudentCustomErrorResponse error = new StudentCustomErrorResponse(System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Student was not found");
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);

    }



}
