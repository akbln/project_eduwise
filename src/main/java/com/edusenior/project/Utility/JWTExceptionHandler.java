//package com.edusenior.project.Utility;
//
//import com.edusenior.project.RestControllers.Student.StudentCustomErrorResponse;
//import io.jsonwebtoken.JwtException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class JWTExceptionHandler {
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<StudentCustomErrorResponse> StudentNotFoundHandler(JwtException ex){
//        StudentCustomErrorResponse error = new StudentCustomErrorResponse(System.currentTimeMillis(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Error Managing Token");
//        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<StudentCustomErrorResponse> StudentNotFoundHandler(Exception ex){
//        StudentCustomErrorResponse error = new StudentCustomErrorResponse(System.currentTimeMillis(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                ex.getMessage());
//        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//}
