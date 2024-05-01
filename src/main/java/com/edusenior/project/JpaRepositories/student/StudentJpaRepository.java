package com.edusenior.project.JpaRepositories.student;



import com.edusenior.project.entities.Users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentJpaRepository extends JpaRepository<Student,String> {
//    public Student findByEmail(String email);
    @Query("SELECT s FROM Student s WHERE s.id IN :studentIds")
    List<Student> findAllById(List<String> studentIds);
//    @Query("SELECT s FROM Student s WHERE s.user.credentials.email IN :emails AND NOT EXISTS " +
//            "(SELECT c FROM s.enrolledClasses c WHERE c.id = :classId)")
//    List<Student> findAllStudentsNotInClassByEmails(List<String> emails,String classId);
}
