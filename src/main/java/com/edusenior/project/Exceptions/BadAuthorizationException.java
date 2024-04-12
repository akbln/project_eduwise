package com.edusenior.project.Exceptions;

public class BadAuthorizationException extends RuntimeException{
    public BadAuthorizationException(String message) {
        super(message);
    }
}
