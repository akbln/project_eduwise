package com.edusenior.project.Utility;

public abstract class ServerResponse {
    private String operationStatus;
    private String description;
    public ServerResponse(String operationStatus, String description){
        this.operationStatus = operationStatus;
        this.description = description;
    }
    public String getOperationStatus() {
        return operationStatus;
    }
    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
