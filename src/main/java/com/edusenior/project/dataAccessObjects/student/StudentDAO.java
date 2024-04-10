package com.edusenior.project.dataAccessObjects.student;


import com.edusenior.project.dataTransferObjects.CredentialsDTO;
import com.edusenior.project.entities.Student;

public interface StudentDAO {
    public Student fetchStudent(String id);
    public Student fetchStudentByEmail(String email);
    public void createStudent(Student s);
    public CredentialsDTO fetchCredentials(String email);
}
