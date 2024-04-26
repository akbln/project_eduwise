package com.edusenior.project.Exceptions;

import java.util.ArrayList;

public class StudentEmailsNotFoundException extends RuntimeException {
    public StudentEmailsNotFoundException(String message,ArrayList<String> notFoundEmails) {
        super(message);
        this.notFoundEmails = notFoundEmails;
    }
    private ArrayList<String> notFoundEmails;

    public ArrayList<String> getNotFoundEmails() {
        return notFoundEmails;
    }

    public void setNotFoundEmails(ArrayList<String> notFoundEmails) {
        this.notFoundEmails = notFoundEmails;
    }
}
