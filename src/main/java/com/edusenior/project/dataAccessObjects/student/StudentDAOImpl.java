package com.edusenior.project.dataAccessObjects.student;

import com.edusenior.project.entities.Users.Student;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAOImpl implements StudentDAO{
    private final EntityManager em;

    @Autowired
    public StudentDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createStudent(Student s) {
        em.persist(s);
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
//    @Override
//    public CredentialsDTO fetchCredentials(String email) {
//        List<CredentialsDTO> results = em.createQuery("SELECT new com.edusenior.project.dataTransferObjects.CredentialsDTO(s.email, s.hash, s.lockout, s.failed)" +
//                                "FROM Student s WHERE s.email = :email", CredentialsDTO.class)
//                .setParameter("email", email)
//                .getResultList();
//        return results.isEmpty() ? null : results.getFirst();
//    }



}
