package com.edusenior.project.dataAccessObjects.student;


import com.edusenior.project.dataAccessObjects.UserDAO;
import com.edusenior.project.entities.Users.Student;

public interface StudentDAO extends UserDAO {
    public Student fetchStudent(String id);
    public Student fetchStudentByEmail(String email);
    public void createStudent(Student s);
//    public CredentialsDTO fetchCredentials(String email);
//    public boolean checkExistingEmail(String email);

}
