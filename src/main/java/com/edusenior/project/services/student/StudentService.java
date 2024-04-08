package com.edusenior.project.services.student;

import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Student;

public interface StudentService {
    public Student fetchStudent(String id);
    public void registerStudent(NewStudentDTO sDTO);
}
