package com.edusenior.project.ServerResponses;

import java.util.ArrayList;
import java.util.List;

public class EmailsNotFoundResponse extends ServerResponse{
    public EmailsNotFoundResponse(String operationStatus, List<String> errors ,ArrayList<String> emails) {
        super(operationStatus, errors);
        this.notFoundEmails = emails;
    }
    private ArrayList<String> notFoundEmails;

    public ArrayList<String> getNotFoundEmails() {
        return notFoundEmails;
    }

    public void setNotFoundEmails(ArrayList<String> notFoundEmails) {
        this.notFoundEmails = notFoundEmails;
    }
}
