package com.edusenior.project.dataAccessObjects.student;

import com.edusenior.project.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StudentDAOImpl implements StudentDAO{
    private final EntityManager em;

    @Autowired
    public StudentDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public String createStudent(Student s) {
//        Student newStudent = new Student(name,age,gender,level);
        em.persist(s);
        return s.getId();
    }
    @Override
    public Student fetchStudent(String id){
        return em.find(Student.class,id);
    }

    @Override
    public Student fetchStudentByEmail(String email) {
        return em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
