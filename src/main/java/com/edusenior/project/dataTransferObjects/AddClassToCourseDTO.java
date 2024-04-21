package com.edusenior.project.dataTransferObjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddClassToCourseDTO {
    @NotNull(message = "Course ID parameter must not be empty")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",message = "Invalid Course ID")
    private String courseId;
    @NotNull(message = "Class ID parameter must not be empty")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",message = "Invalid Class ID")
    private String classId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
