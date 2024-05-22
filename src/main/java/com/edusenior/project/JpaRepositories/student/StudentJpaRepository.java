package com.edusenior.project.JpaRepositories.student;



import com.edusenior.project.entities.Users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentJpaRepository extends JpaRepository<Student,String> {
    @Query("SELECT s FROM Student s WHERE s.id IN :studentIds")
    List<Student> findAllById(List<String> studentIds);
}
