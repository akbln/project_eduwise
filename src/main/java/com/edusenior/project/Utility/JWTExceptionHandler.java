package com.edusenior.project.Utility;

import com.edusenior.project.Exceptions.BadAuthorizationException;
import com.edusenior.project.RestControllers.Student.StudentCustomErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class JWTExceptionHandler {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ServerResponse> handleJWT(JwtException ex){
        List<String> errors = new ArrayList<>();
        errors.add("Error with JWT");
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadAuthorizationException.class)
    public ResponseEntity<ServerResponse> handleBadJWT(BadAuthorizationException ex){
        List<String> errors = new ArrayList<>();
        errors.add("Invalid JWT");
        ServerResponse error = new ServerResponse("failed",errors);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
