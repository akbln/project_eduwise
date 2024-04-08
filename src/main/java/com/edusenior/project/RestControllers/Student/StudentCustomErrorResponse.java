package com.edusenior.project.RestControllers.Student;

public class StudentCustomErrorResponse {
    private long timeStamp;
    private int httpStatus;
    private String message;

    public StudentCustomErrorResponse(long timeStamp, int httpStatus, String message) {
        this.timeStamp = timeStamp;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

