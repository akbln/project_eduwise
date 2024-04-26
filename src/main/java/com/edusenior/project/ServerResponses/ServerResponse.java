package com.edusenior.project.ServerResponses;

import java.util.List;

public class ServerResponse {
    private String operationStatus;
    private List<String> errors;

    public ServerResponse(String operationStatus, List<String> errors){
        this.operationStatus = operationStatus;
        this.errors = errors;
    }
    public String getOperationStatus() {
        return operationStatus;
    }
    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
