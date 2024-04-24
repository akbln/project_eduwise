package com.edusenior.project.dataAccessObjects.student;


import com.edusenior.project.dataAccessObjects.UserDAO;
import com.edusenior.project.entities.Users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface StudentDAO extends JpaRepository<Student,String> {
    public Student findByEmail(String email);
    List<Student> findAllById(List<String> studentIds);
}
