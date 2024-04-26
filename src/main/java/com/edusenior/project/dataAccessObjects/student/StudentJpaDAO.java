package com.edusenior.project.dataAccessObjects.student;



import com.edusenior.project.entities.Users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentJpaDAO extends JpaRepository<Student,String> {
//    public Student findByEmail(String email);
    @Query("SELECT s FROM Student s WHERE s.id IN :studentIds")
    List<Student> findAllById(List<String> studentIds);
}
