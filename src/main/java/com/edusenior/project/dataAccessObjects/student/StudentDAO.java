package com.edusenior.project.dataAccessObjects.student;


import com.edusenior.project.entities.Student;

import java.util.UUID;

public interface StudentDAO {
    public Student fetchStudent(String id);
    public Student fetchStudentByEmail(String email);
    public String createStudent(Student s);
}
