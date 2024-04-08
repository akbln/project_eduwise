package com.edusenior.project.RestControllers.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
