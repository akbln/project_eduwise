package com.edusenior.project.dataTransferObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public class AddMultipleStudentsToCourseDTO {
    @NotNull
    @NotEmpty
    private ArrayList<String> emails;
    @NotNull
    private String classId;

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
